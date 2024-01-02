package com.example.note_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class font_day extends AppCompatActivity {
    private TextView selectFontTextView;
    private TextView chooseSizeTextView;
    private RadioButton radioSmall;
    private RadioButton radioMedium;
    private RadioButton radioBig;
    private RadioButton radioSerif;
    private RadioButton radioDancing;
    private RadioButton radioMonospace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_day);

        selectFontTextView = findViewById(R.id.selectFontTextView);
        chooseSizeTextView = findViewById(R.id.chooseSizeTextView);
        radioSmall = findViewById(R.id.radioSmall);
        radioMedium = findViewById(R.id.radioMedium);
        radioBig = findViewById(R.id.radioBig);
        radioSerif = findViewById(R.id.radioSerif);
        radioDancing = findViewById(R.id.radioDancing);
        radioMonospace = findViewById(R.id.radioMonospace);
        int originalSelectFontTextStyle = selectFontTextView.getTypeface().getStyle();
        int originalChooseSizeTextStyle = chooseSizeTextView.getTypeface().getStyle();
        //Button applyButton = findViewById(R.id.applyButton);

        RadioGroup fontRadioGroup = findViewById(R.id.fontRadioGroup);
        fontRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Typeface typeface;
            if (checkedId == R.id.radioSerif) {
                typeface = Typeface.DEFAULT;
            } else if (checkedId == R.id.radioMonospace) {
                typeface = Typeface.MONOSPACE;
            } else if (checkedId == R.id.radioDancing) {
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
            radioMonospace.setTypeface(typeface);
            radioSerif.setTypeface(typeface);
            radioDancing.setTypeface(typeface);
            //applyButton.setTypeface(typeface);

            // Làm mới lại giao diện
            selectFontTextView.invalidate();
            chooseSizeTextView.invalidate();

            // Lưu trữ cài đặt font chữ
            saveFontSettings(typeface);
        });

        RadioGroup textSizeRadioGroup = findViewById(R.id.textSizeRadioGroup);
        textSizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            float textSize;
            if (checkedId == R.id.radioSmall) {
                textSize = 18;
            } else if (checkedId == R.id.radioMedium) {
                textSize = 24;
            } else {
                textSize = 30;
            }

            selectFontTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            chooseSizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioSerif.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioDancing.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioMonospace.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioSmall.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioMedium.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioBig.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

            //applyButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

            // Lưu trữ cài đặt kích thước chữ
            saveTextSizeSettings(textSize);
        });


        // Khôi phục cài đặt font chữ và kích thước chữ
        restoreSettings();
    }
    // Hàm lưu trữ cài đặt font chữ
    private void saveFontSettings(Typeface typeface) {
        if (typeface != null && typeface != Typeface.DEFAULT) {
            SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String fontName = typeface.toString();
            editor.putString("selectedFont", fontName);;
            editor.apply();
        }
    }



    // Hàm lưu trữ cài đặt kích thước chữ
    private void saveTextSizeSettings(float textSize) {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("selectedTextSize", textSize);
        editor.apply();
    }

    // Hàm khôi phục cài đặt font chữ và kích thước chữ
    private void restoreSettings() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        // Khôi phục font chữ
        String fontName = preferences.getString("selectedFont", null);
        Typeface typeface = Typeface.DEFAULT;
        if (fontName != null) {
            try {
                typeface = Typeface.createFromFile(fontName);
            } catch (Exception e) {
                Log.e("FontDayActivity", "Failed to create typeface from file", e);
                Toast.makeText(getApplicationContext(), "Failed to create typeface from file", Toast.LENGTH_SHORT).show();
            }
        }

        selectFontTextView.setTypeface(typeface);
        chooseSizeTextView.setTypeface(typeface);
        radioSmall.setTypeface(typeface);
        radioMedium.setTypeface(typeface);
        radioBig.setTypeface(typeface);
        radioDancing.setTypeface(typeface);
        radioSerif.setTypeface(typeface);
        radioMonospace.setTypeface(typeface);

        // Khôi phục kích thước chữ
        float textSize = preferences.getFloat("selectedTextSize", 16);
        selectFontTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        chooseSizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioSerif.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioDancing.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioMonospace.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioSmall.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioMedium.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioBig.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }



    /*public void applyFontAndTextSizeToAllViews(ViewGroup viewGroup, String fontFamily, float textSizeOption) {
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
    }*/
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
    public void setting_day(View view) {
        Intent intent = new Intent(this, setting_day.class);
        startActivity(intent);
        finish();
    }
    public void setting_night(View view){
        Intent intent = new Intent(this, setting_night.class);
        startActivity(intent);
        finish();
    }
}
