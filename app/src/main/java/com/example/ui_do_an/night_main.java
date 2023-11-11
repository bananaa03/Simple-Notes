package com.example.ui_do_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class night_main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_main);

        // Các xử lý khác nếu cần thiết cho layout mới
    }
    public void day_main(View view){

        Intent intent = new Intent(this, day_main.class);
        startActivity(intent);
    }
}
