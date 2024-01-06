package com.example.note_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class reminder_take extends AppCompatActivity {
    Button btnPickDate;
    Button btnPickTime;
    EditText editTextDate;
    EditText editTextTime;
    EditText editTextContent;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, nDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_take);

        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);
        editTextDate = findViewById(R.id.date);
        editTextTime = findViewById(R.id.time);
        editTextContent=findViewById(R.id.edt_content);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userUID = currentUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("reminder").child(userUID);
            String key = mDatabase.push().getKey();
            nDatabase = mDatabase.child(key);
        }

        // Lấy nội dung titile của intent note_take hiện lên edit text
        Intent intent = getIntent();
        String contentIntentFromNotetake = intent.getStringExtra("Title");
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
        if (!date.isEmpty() && !time.isEmpty()) {
            nDatabase.child("Date").setValue(date + " " + time);
            nDatabase.child("Content").setValue(content);
        }
        Toast.makeText(this, "Đã lưu nhắc nhở" , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(reminder_take.this, reminder.class);
        startActivity(intent);
    }
    public void delete(View view){
        if (nDatabase != null) {
            nDatabase.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(reminder_take.this, "Đã xóa nhắc nhở", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(reminder_take.this, reminder.class);
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
        Intent intent = new Intent(reminder_take.this, reminder.class);
        startActivity(intent);
        finish();
    }
}
