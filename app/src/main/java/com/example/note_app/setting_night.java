package com.example.note_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class setting_night extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_night);

        // Đọc các cài đặt font và size từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        String fontName = preferences.getString("selectedFont", null);
        Typeface typeface = Typeface.DEFAULT;
        if (fontName != null) {
            try {
                typeface = Typeface.createFromAsset(getAssets(), fontName);
            } catch (Exception e) {
                Log.e("SettingDayActivity", "Failed to create typeface from file", e);
                Toast.makeText(getApplicationContext(), "Failed to create typeface from file", Toast.LENGTH_SHORT).show();
            }
        }

        float textSize = preferences.getFloat("selectedTextSize", 16);

        Button thongke = findViewById(R.id.thongke);
        Button dongbo = findViewById(R.id.dongbo);
        Button font = findViewById(R.id.font);
        Button manage_user = findViewById(R.id.manage_user);

        thongke.setTypeface(typeface);
        thongke.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        dongbo.setTypeface(typeface);
        dongbo.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        font.setTypeface(typeface);
        font.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        manage_user.setTypeface(typeface);
        manage_user.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }
    public void btnBacktoMain_night(View view){
        Intent intent = new Intent(this, night_main.class);
        startActivity(intent);
        finish();
    }
    public void login_night(View view){
        if (firebaseAuth.getCurrentUser() != null) {
            // Người dùng đã đăng nhập, chuyển tới user.class
            Intent intent = new Intent(this, user_night.class);
            startActivity(intent);
            finish();
        } else {
            // Người dùng chưa đăng nhập, chuyển tới trang đăng nhập (log_in.class)
            Intent intent = new Intent(this, log_in.class);
            startActivity(intent);
            finish();
        }
    }
    public void day_main(View view){
        Intent intent = new Intent(this, day_main.class);
        startActivity(intent);
        finish();
    }

    public void day_setting (View view){
        Intent intent = new Intent(this, setting_day.class);
        startActivity(intent);
        finish();
    }
    public void font_night(View view){
        Intent intent = new Intent(this, font_night.class);
        startActivity(intent);
        finish();
    }
}