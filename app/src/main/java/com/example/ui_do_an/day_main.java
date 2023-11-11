package com.example.ui_do_an;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
public class day_main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_main);

        // Các xử lý khác nếu cần thiết cho layout mới
    }
    public void night_main(View view){

        Intent intent = new Intent(this, night_main.class);
        startActivity(intent);
    }
}
