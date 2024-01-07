package com.example.note_app;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reminder_take extends AppCompatActivity {
    Button btnPickDate;
    Button btnPickTime;
    EditText editTextDate;
    EditText editTextTime;
    EditText editTextContent;
    private EditText edtContent, edtDate, edtTime;
    private CheckBox checkBoxAlarm;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, nDatabase;
    String key;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_take);
        edtContent = findViewById(R.id.edt_content);
        edtDate = findViewById(R.id.date);
        edtTime = findViewById(R.id.time);
        checkBoxAlarm = findViewById(R.id.alarm);

        // Khởi tạo AlarmManager và NotificationManager
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            key = intent.getStringExtra("reminder_id");
            String title = intent.getStringExtra("title");
            String date = intent.getStringExtra("date");
            String time = intent.getStringExtra("time");

            // Hiển thị dữ liệu lên giao diện
            edtContent.setText(title);
            edtDate.setText(date);
            edtTime.setText(time);

        }

        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);
        editTextDate = findViewById(R.id.date);
        editTextTime = findViewById(R.id.time);
        editTextContent = findViewById(R.id.edt_content);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userUID = currentUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("reminder").child(userUID);
            if(key == null){
            key = mDatabase.push().getKey();
            }
            nDatabase = mDatabase.child(key);
        }

        // Lấy nội dung titile của intent note_take hiện lên edit text
        Intent intent1 = getIntent();
        String contentIntentFromNotetake = intent1.getStringExtra("Title");
        editTextContent.setText(contentIntentFromNotetake);

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String selectedDate = dateFormat.format(calendar.getTime());
                editTextDate.setText(selectedDate);
            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                String selectedTime = timeFormat.format(calendar.getTime());
                editTextTime.setText(selectedTime);
            }
        };


    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                reminder_take.this, dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                reminder_take.this, timeSetListener,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }


    public void save(View view) {
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        boolean alarmChecked = checkBoxAlarm.isChecked();
        Reminder reminder = new Reminder();
        reminder.setAlarmOn(alarmChecked);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (!date.isEmpty() && !time.isEmpty()) {
            // Sử dụng key để xác định reminder cần chỉnh sửa trong Firebase Realtime Database
            DatabaseReference reminderRef = FirebaseDatabase.getInstance().getReference()
                    .child("reminder").child(currentUser.getUid()).child(key);

            // Update dữ liệu của reminder tương ứng trong Firebase Realtime Database
            Map<String, Object> reminderUpdates = new HashMap<>();
            reminderUpdates.put("Content", content);
            reminderUpdates.put("Date", date);
            reminderUpdates.put("Time", time);
            reminderUpdates.put("Alarm", alarmChecked);

            reminderRef.updateChildren(reminderUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(reminder_take.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(reminder_take.this, reminder_list.class);
                            startActivity(intent);
                            finish(); // Đóng activity sau khi chỉnh sửa
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(reminder_take.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Vui lòng nhập ngày và giờ", Toast.LENGTH_SHORT).show();
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        try {
            Date dateTime = dateFormat.parse(date + " " + time);
            if (dateTime != null) {
                calendar.setTime(dateTime);

                // Nếu checkbox được chọn, đặt báo thức
                if (alarmChecked) {
                    setAlarm(calendar.getTimeInMillis());
                } else {
                    // Nếu không được chọn, gửi thông báo
                    sendNotification(calendar.getTimeInMillis());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void delete(View view){
        if (nDatabase != null) {
            nDatabase.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(reminder_take.this, "Đã xóa nhắc nhở", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(reminder_take.this, reminder_list.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(reminder_take.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void returned(View view){
        Intent intent = new Intent(reminder_take.this, reminder_list.class);
        startActivity(intent);
        finish();
    }

    private void setAlarm(long timeInMillis) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, FLAG_IMMUTABLE);

        // Đặt báo thức
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, alarmIntent);
        Toast.makeText(this, "Báo thức được đặt", Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(long timeInMillis) {
        // Tạo thông báo
        Intent intent = new Intent(this, ReminderNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, FLAG_IMMUTABLE);

        // ... (Tạo notification)

        // Hiển thị thông báo tại thời điểm xác định
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Channel name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new Notification.Builder(this, "default")
                .setContentTitle("Thông báo")
                .setContentText("Nội dung thông báo")
                .setSmallIcon(R.drawable.alarm)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0, notification);
    }
}
