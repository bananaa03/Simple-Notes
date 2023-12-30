package com.example.note_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class setting_day extends AppCompatActivity {


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
        Intent intent = new Intent(this, log_in.class);
        startActivity(intent);
    }
}