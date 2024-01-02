package com.example.note_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class font_day extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_day);
        TextView selectFontTextView = findViewById(R.id.selectFontTextView);
        TextView chooseSizeTextView = findViewById(R.id.chooseSizeTextView);
        TextView radioSmall = findViewById(R.id.radioSmall);
        TextView radioMedium = findViewById(R.id.radioMedium);
        TextView radioBig = findViewById(R.id.radioBig);
        TextView radioSerif = findViewById(R.id.radioSerif);
        TextView radioCasual = findViewById(R.id.radioCasual);
        TextView radioMonospace = findViewById(R.id.radioMonospace);
        Button applyButton = findViewById(R.id.applyButton);

        RadioGroup fontRadioGroup = findViewById(R.id.fontRadioGroup);
        fontRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Typeface typeface;
                if (checkedId == R.id.radioSerif) {
                    typeface = Typeface.DEFAULT;
                } else if (checkedId == R.id.radioMonospace) {
                    typeface = Typeface.MONOSPACE;
                } else if (checkedId == R.id.radioCasual) {
                    typeface = getResources().getFont(R.font.dancing_script_bold);
                } else {
                    // Mặc định sử dụng phông chữ mặc định
                    typeface = Typeface.DEFAULT;
                }

                selectFontTextView.setTypeface(typeface);
                chooseSizeTextView.setTypeface(typeface);
                radioSmall.setTypeface(typeface);
                radioMedium.setTypeface(typeface);
                radioBig.setTypeface(typeface);
                applyButton.setTypeface(typeface);
            }
        });

        RadioGroup textSizeRadioGroup = findViewById(R.id.textSizeRadioGroup);
        textSizeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                float textSize;
                if (checkedId == R.id.radioSmall) {
                    textSize = 16;
                } else if (checkedId == R.id.radioMedium) {
                    textSize = 20;
                } else {
                    textSize = 24;
                }

                selectFontTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                chooseSizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                radioSerif.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                radioCasual.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                radioMonospace.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                applyButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            }
        });
    }
    public void applyFontAndTextSizeToAllViews(ViewGroup viewGroup, String fontFamily, float textSizeOption) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);

            if (childView instanceof ViewGroup) {
                // Nếu là ViewGroup, tiếp tục duyệt qua các View con
                applyFontAndTextSizeToAllViews((ViewGroup) childView, fontFamily, textSizeOption);
            } else if (childView instanceof TextView) {
                // Nếu là TextView, áp dụng thay đổi phông chữ và kích thước chữ
                TextView textView = (TextView) childView;
                // Lưu lại kích thước chữ ban đầu
                float originalTextSize = textView.getTextSize();

                // Tùy chọn kích thước chữ
                float newTextSize;
                if (textSizeOption == 1.5) {
                    newTextSize = originalTextSize * 1.5f;
                } else if (textSizeOption == 1.75) {
                    newTextSize = originalTextSize * 1.75f;
                } else {
                    newTextSize = originalTextSize;
                }

                textView.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
            }
        }
    }
    public void day_main(View view){
        Intent intent = new Intent(this, day_main.class);
        startActivity(intent);
        finish();
    }
    public void font_night(View view){
        Intent intent = new Intent(this, font_night.class);
        startActivity(intent);
        finish();
    }
    public void setting_day(View view){
        Intent intent = new Intent(this, setting_day.class);
        startActivity(intent);
        finish();
    }
}
