package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import com.google.firebase.database.ValueEventListener;

public class user_night extends AppCompatActivity {

    TextView Email, Username;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_night);
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
                    Toast.makeText(user_night.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Nếu người dùng không đăng nhập, bạn có thể chuyển hướng hoặc xử lý một cách phù hợp
            // Ví dụ: chuyển hướng đến trang đăng nhập
            Intent intent = new Intent(user_night.this, log_in.class);
            startActivity(intent);
            finish();
        }
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

        TextView textView= findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        TextView username = findViewById(R.id.username);
        TextView textView3 = findViewById(R.id.textView3);
        TextView email = findViewById(R.id.email);
        Button changepsw = findViewById(R.id.changepasw);
        Button logout = findViewById(R.id.logout);

        textView.setTypeface(typeface);
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView2.setTypeface(typeface);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView3.setTypeface(typeface);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        username.setTypeface(typeface);
        username.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        email.setTypeface(typeface);
        email.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        changepsw.setTypeface(typeface);
        changepsw.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        logout.setTypeface(typeface);
        logout.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); // Đăng xuất người dùng
        Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(user_night.this, setting_day.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Xóa các Activity trước đó khỏi Stack
        startActivity(intent);
        finish(); // Đóng Activity hiện tại
    }
    public void changepass(View view) {
        // Chuyển hướng đến màn hình đổi mật khẩu
        Intent intent = new Intent(user_night.this, changePassword.class);
        startActivity(intent);
        finish();
    }
    public void setting_night(View view){
        Intent intent = new Intent(this, setting_night.class);
        startActivity(intent);
        finish();
    }
    public void day_user(View view){
        Intent intent = new Intent(this, user_day.class);
        startActivity(intent);
        finish();
    }
    public void night_main(View view){
        Intent intent = new Intent(this, night_main.class);
        startActivity(intent);
        finish();
    }
}