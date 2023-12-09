package com.example.ui_do_an;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class day_main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_main);
        RecyclerView recyclerView;
        ArrayList<Note> listNote;
        NoteAdapter noteAdapter;

        // Các xử lý khác nếu cần thiết cho layout mới
        recyclerView= findViewById(R.id.rcv_note);

        listNote= new ArrayList<>();
        listNote.add(new Note("b", "b"));
        listNote.add(new Note("bc", "cb"));
        listNote.add(new Note("a", "bd"));
        listNote.add(new Note("d", "b"));
        Log.d("ItemCount", "Number of items: " + listNote.size());
        noteAdapter= new NoteAdapter(this, R.layout.item_note, listNote);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);
    }
    public void night_main(View view){

        Intent intent = new Intent(this, night_main.class);
        startActivity(intent);
    }
    public void setting_day(View view){
        Intent intent = new Intent(this, setting_day.class);
        startActivity(intent);
    }
}
