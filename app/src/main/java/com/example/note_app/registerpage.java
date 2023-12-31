package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerpage extends AppCompatActivity {

    TextInputEditText iedtEmail2, iedtPassword2, iedtUsername;
    Button btnsignUp;
    TextView tvsignIn;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        iedtUsername=findViewById(R.id.input_username);
        iedtEmail2=findViewById(R.id.input_email2);
        iedtPassword2=findViewById(R.id.input_passwd2);
        btnsignUp=findViewById(R.id.btn_signup);
        tvsignIn=findViewById(R.id.tv_signin);

        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        tvsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(registerpage.this, log_in.class);
                startActivity(intent);
                finish();
            }
        });
        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email2, password2, username;
                email2 = iedtEmail2.getText().toString().trim();
                password2 = iedtPassword2.getText().toString().trim();
                username = iedtUsername.getText().toString().trim();
                if (TextUtils.isEmpty(email2)) {
                    Toast.makeText(registerpage.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    Toast.makeText(registerpage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(registerpage.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email2,password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    // Lấy UID của người dùng vừa đăng ký
                                    String userUID = firebaseAuth.getCurrentUser().getUid();

                                    // Tạo một nút con (node) trong Realtime Database với key là userUID
                                    DatabaseReference currentUserDb = databaseReference.child(userUID);

                                    // Lưu thông tin người dùng vào Realtime Database
                                    currentUserDb.child("email").setValue(email2);
                                    currentUserDb.child("username").setValue(username);
                                    currentUserDb.child("password").setValue(password2);

                                    Toast.makeText(registerpage.this, "Registration Successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(registerpage.this, log_in.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(registerpage.this, "Registration failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });




    }
}