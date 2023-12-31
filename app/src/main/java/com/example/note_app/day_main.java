package com.example.note_app;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class day_main extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Note> listNote;
    NoteAdapter noteAdapter;
    SearchView searchView;
    ImageButton btnAdd, btnFindNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_main);


        // Các xử lý khác nếu cần thiết cho layout mới
        recyclerView= findViewById(R.id.rcv_note);
        searchView = findViewById(R.id.search_View);


        recyclerView.setHasFixedSize(false);
        listNote= new ArrayList<>();
        listNote.add(new Note("b", "b"));
        listNote.add(new Note("bc", "cb"));
        listNote.add(new Note("a", "bd"));
        listNote.add(new Note("d", "b"));
        listNote.add(new Note("Tran", "b"));
        listNote.add(new Note("Tran Chau", "cb"));
        listNote.add(new Note("Chau A", "bd"));
        listNote.add(new Note("Nguyen Tran", "b"));
        listNote.add(new Note("b", "b"));
        listNote.add(new Note("bc", "cb"));
        listNote.add(new Note("a", "bd"));
        listNote.add(new Note("d", "b"));
        listNote.add(new Note("Tran", "b"));
        listNote.add(new Note("Tran Chau", "cb"));
        listNote.add(new Note("Chau A", "bd"));
        listNote.add(new Note("Nguyen Tran", "b"));
        Log.d("ItemCount", "Number of items: " + listNote.size());
        noteAdapter= new NoteAdapter(this, R.layout.item_note, listNote);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                noteAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btnAdd = (ImageButton) findViewById(R.id.iBt_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewNote();
            }
        });

        btnFindNote = (ImageButton) findViewById(R.id.imageButtonFindNote);
        btnFindNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNewNote();
            }
        });

    }
    public void night_main(View view){

        Intent intent = new Intent(this, night_main.class);
        startActivity(intent);
    }
    public void setting_day(View view){
        Intent intent = new Intent(this, setting_day.class);
        startActivity(intent);
    }
    public void openNewNote(){
        Intent intent = new Intent(day_main.this, note_day.class);
        startActivity(intent);
    }

    public void findNewNote(){
        Intent intent = new Intent(day_main.this, note_day.class);
        startActivity(intent);
    }
}