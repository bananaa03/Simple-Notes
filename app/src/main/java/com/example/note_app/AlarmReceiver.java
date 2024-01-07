package com.example.note_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Lấy tiêu đề của reminder từ intent
        String title = intent.getStringExtra("title");

        // Hiển thị thông báo Toast với tiêu đề của reminder
        Toast.makeText(context, "Báo thức: " + title, Toast.LENGTH_LONG).show();

        // Phát âm thanh từ raw resource (ví dụ: sound.mp3 trong thư mục raw)
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sound);
        mediaPlayer.start();

        // Rung thiết bị
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(2000); // Rung trong 2 giây
        }
    }
}

