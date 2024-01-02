package com.example.note_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class font_night extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_night);
    }
    public void night_main(View view){
        Intent intent = new Intent(this, night_main.class);
        startActivity(intent);
        finish();
    }
    public void font_day(View view){
        Intent intent = new Intent(this, font_day.class);
        startActivity(intent);
        finish();
    }
    public void setting_night(View view){
        Intent intent = new Intent(this, setting_night.class);
        startActivity(intent);
        finish();
    }
}
