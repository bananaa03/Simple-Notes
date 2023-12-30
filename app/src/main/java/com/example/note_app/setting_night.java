package com.example.note_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class setting_night extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_night);
    }
    public void btnBacktoMain_night(View view){
        Intent intent = new Intent(this, night_main.class);
        startActivity(intent);
    }
}