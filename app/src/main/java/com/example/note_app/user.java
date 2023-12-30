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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user extends AppCompatActivity {

    TextView Email, Username;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        Username=findViewById(R.id.username);
        Email=findViewById(R.id.email);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            String userUID = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userUID);

            // Lắng nghe sự thay đổi trong dữ liệu
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Kiểm tra xem có dữ liệu không
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ dataSnapshot và hiển thị lên TextView
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String username = dataSnapshot.child("username").getValue(String.class);

                        Email.setText(email);
                        Username.setText(username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
                    Toast.makeText(user.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Nếu người dùng không đăng nhập, bạn có thể chuyển hướng hoặc xử lý một cách phù hợp
            // Ví dụ: chuyển hướng đến trang đăng nhập
            Intent intent = new Intent(user.this, log_in.class);
            startActivity(intent);
            // finish();
        }

    }
}