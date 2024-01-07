package com.example.note_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class reminder_list extends AppCompatActivity implements ReminderAdapter.OnItemClickListener{
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private List<Reminder> reminderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);

        // Khởi tạo RecyclerView và adapter
        recyclerView = findViewById(R.id.rcv_remind);
        reminderList = new ArrayList<>();
        adapter = new ReminderAdapter(reminderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        // Khởi tạo Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userUID = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("reminder").child(userUID);

            // Lắng nghe sự thay đổi dữ liệu từ Firebase Realtime Database
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    reminderList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        String title = snapshot.child("Content").getValue(String.class);
                        String date = snapshot.child("Date").getValue(String.class);
                        String time = snapshot.child("Time").getValue(String.class);
                        Reminder reminder = new Reminder(title, date, time);
                        reminder.setKey(key);
                        reminderList.add(reminder);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("YourActivity", "Error: " + databaseError.getMessage());
                }
            });
        }
    }
    @Override
    public void onItemClick(int position) {
        Reminder clickedReminder = reminderList.get(position);
        String reminderKey = clickedReminder.getKey();
        Intent intent = new Intent(this, reminder_take.class);
        intent.putExtra("reminder_id", reminderKey);
        intent.putExtra("title", clickedReminder.getTitle());
        intent.putExtra("date", clickedReminder.getDate());
        intent.putExtra("time", clickedReminder.getTime());
        startActivity(intent);
    }

    public void new_reminder(View view){
        if(currentUser==null){
            Toast.makeText(this, "Vui lòng đăng nhập để bắt đầu nhắc nhở", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, log_in.class));
            finish();
        }else {
            Intent intent = new Intent(reminder_list.this, reminder_take.class);
            startActivity(intent);
            finish();
        }
    }
    public void main(View view){
        Intent intent = new Intent(this, main.class);
        startActivity(intent);
        finish();
    }
    public void setting(View view){
        Intent intent = new Intent(this, setting.class);
        startActivity(intent);
        finish();
    }
}
