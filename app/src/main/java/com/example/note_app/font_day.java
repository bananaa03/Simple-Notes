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
    private RadioButton radioOpen_sans;
    private RadioButton radioDancing;
    private RadioButton radioComfortaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_day);

        selectFontTextView = findViewById(R.id.selectFontTextView);
        chooseSizeTextView = findViewById(R.id.chooseSizeTextView);
        radioSmall = findViewById(R.id.radioSmall);
        radioMedium = findViewById(R.id.radioMedium);
        radioBig = findViewById(R.id.radioBig);
        radioOpen_sans = findViewById(R.id.radioOpen_sans);
        radioDancing = findViewById(R.id.radioDancing);
        radioComfortaa = findViewById(R.id.radioComfortaa);

        RadioGroup fontRadioGroup = findViewById(R.id.fontRadioGroup);
        fontRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Typeface typeface;
            if (checkedId == R.id.radioOpen_sans) {
                typeface = Typeface.createFromAsset(getAssets(), "open_sans.ttf");
            } else if (checkedId == R.id.radioComfortaa) {
                typeface = Typeface.createFromAsset(getAssets(), "comfortaa.ttf");
            } else if (checkedId == R.id.radioDancing) {
                typeface = Typeface.createFromAsset(getAssets(), "dancing_script_bold.ttf");
            } else {
                // Mặc định sử dụng phông chữ mặc định
                typeface = Typeface.DEFAULT;
            }

            selectFontTextView.setTypeface(typeface);
            chooseSizeTextView.setTypeface(typeface);
            radioSmall.setTypeface(typeface);
            radioMedium.setTypeface(typeface);
            radioBig.setTypeface(typeface);

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

            //selectFontTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            //chooseSizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioOpen_sans.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioDancing.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            radioComfortaa.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

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
            String fontName;
            if (typeface.equals(Typeface.createFromAsset(getAssets(), "open_sans.ttf"))) {
                fontName = "open_sans.ttf";
            } else if (typeface.equals(Typeface.createFromAsset(getAssets(), "comfortaa.ttf"))) {
                fontName = "comfortaa.ttf";
            } else if (typeface.equals(Typeface.createFromAsset(getAssets(), "dancing_script_bold.ttf"))) {
                fontName = "dancing_script_bold.ttf";
            } else {
                fontName = "";
            }
            editor.putString("selectedFont", fontName);
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
                typeface = Typeface.createFromAsset(getAssets(), fontName);
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

        // Khôi phục kích thước chữ
        float textSize = preferences.getFloat("selectedTextSize", 16);
        //selectFontTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        //chooseSizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioOpen_sans.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioDancing.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        radioComfortaa.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
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
