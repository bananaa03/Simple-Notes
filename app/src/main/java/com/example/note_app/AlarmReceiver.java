package com.example.note_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Báo thức: ", Toast.LENGTH_LONG).show();

        // Tạo Notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
//                        .setContentTitle("Báo thức: " + title)
//                        .setContentText("Nội dung thông báo")
//                        .setSmallIcon(R.drawable.alarm);

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
