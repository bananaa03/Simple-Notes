package com.example.note_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class note_night extends AppCompatActivity {

    private ImageButton buttonSetting, btnShare;
    EditText edtnotetitle, edtnotecontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_night);
        edtnotetitle = (EditText) findViewById(R.id.edt_note_title);
        edtnotecontent= (EditText) findViewById(R.id.edt_note_content);

        buttonSetting = (ImageButton) findViewById(R.id.ImageButtonSetting);
        btnShare = (ImageButton) findViewById(R.id.imgbtn_share);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSetting();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteTitle = edtnotetitle.getText().toString();
                String noteContent = edtnotecontent.getText().toString();
                String fullNote = noteTitle+"Chủ nhật"+"\n\n"+ noteContent+"Hi";

                //Tạo intent chia sẻ nội dung
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, fullNote);

                startActivity(Intent.createChooser(shareIntent,"Chia sẻ nội dung ghi chú"));
            }
        });

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

        EditText edt_note_title = findViewById(R.id.edt_note_title);
        TextView note_day =  findViewById(R.id.note_day);
        TextView count_character_note = findViewById(R.id.count_character_note);
        EditText edt_note_content = findViewById(R.id.edt_note_content);
        TextView deleteNote = findViewById(R.id.deleteNote);

        edt_note_title.setTypeface(typeface);
        //edt_note_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        note_day.setTypeface(typeface);
        note_day.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        count_character_note.setTypeface(typeface);
        count_character_note.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        edt_note_content.setTypeface(typeface);
        edt_note_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        deleteNote.setTypeface(typeface);
        deleteNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }
    public void openSetting()
    {
        Intent intent = new Intent(this, note_night.class);
        startActivity(intent);
        finish();
    }
}
