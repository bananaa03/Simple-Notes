package com.example.ui_do_an;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class note_day extends AppCompatActivity {

    private ImageButton buttonSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_day);

        buttonSetting = (ImageButton) findViewById(R.id.ImageButtonSetting);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSetting();
            }
        });
    }
    public void openSetting()
    {
        Intent intent = new Intent(this, setting_note.class);
        startActivity(intent);
    }
}
