package com.example.note_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class night_main extends AppCompatActivity {
    ImageButton buttonNightAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_main);

        // Các xử lý khác nếu cần thiết cho layout mới
        buttonNightAdd = (ImageButton) findViewById(R.id.iBt_Add);
        buttonNightAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewNightNote();
            }
        });
    }
    public void day_main(View view){

        Intent intent = new Intent(this, day_main.class);
        startActivity(intent);
    }

    public void setting_night(View view){
        Intent intent = new Intent(this, setting_night.class);
        startActivity(intent);
    }

    public void openNewNightNote(){
        Intent intent = new Intent(night_main.this, note_night.class);
        startActivity(intent);
    }
}
