package com.example.note_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class setting_day extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_day);


    }
    public void btnBacktoMain_day(View view){
        Intent intent = new Intent(this, day_main.class);
        startActivity(intent);
    }
    public void login(View view){
        if (firebaseAuth.getCurrentUser() != null) {
            // Người dùng đã đăng nhập, chuyển tới user.class
            Intent intent = new Intent(this, user.class);
            startActivity(intent);
        } else {
            // Người dùng chưa đăng nhập, chuyển tới trang đăng nhập (log_in.class)
            Intent intent = new Intent(this, log_in.class);
            startActivity(intent);
        }
    }
}