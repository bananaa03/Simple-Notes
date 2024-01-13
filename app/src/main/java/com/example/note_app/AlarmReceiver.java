package com.example.note_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        // Tạo NotificationManager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Kiểm tra phiên bản Android để tạo kênh thông báo (required từ Android 8.0 trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            CharSequence channelName = "Reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Tạo Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setContentTitle("Báo thức: " + title)
                .setContentText(date + " " + time)
                .setSmallIcon(R.drawable.alarm)
                .setAutoCancel(true);

        // Thêm âm thanh từ resource raw
        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("channel_id");
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        } else {
            builder.setSound(soundUri);
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        }

        // Rung thiết bị
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(VibrationEffect.createOneShot(3000, VibrationEffect.DEFAULT_AMPLITUDE));
        }

        // Hiển thị thông báo
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
