package com.example.note_app;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class day_main extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Note> listNote;
    NoteAdapter22 noteAdapter;
    SearchView searchView;
    ImageButton btnAdd, btnFindNote;
    TextView btnDelete;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_main);

        // Các xử lý khác nếu cần thiết cho layout mới
        recyclerView= findViewById(R.id.rcv_note);
        searchView = findViewById(R.id.search_View);

        setupRecycleView();


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

        btnAdd = (ImageButton) findViewById(R.id.iBt_Add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewNote();
                Utility.getCollectionReferenceForNotes()
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                ArrayList<Note> notesList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : value) {
                                    Note note = document.toObject(Note.class);
                                    notesList.add(note);
                                }
                                // Xử lý dữ liệu notesList, ví dụ: cập nhật RecyclerView
                                loadRecyclerViewAdapter(notesList);
                            }
                        });
            }
        });

        btnFindNote = (ImageButton) findViewById(R.id.iBtFindNote);
        btnFindNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNewNote();
            }
        });
        /*
        btnDelete = findViewById(R.id.btnDeleteNote);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */


        // Save note




    }

    private void setupRecycleView() {
        /*
        recyclerView.setHasFixedSize(false);
        listNote= new ArrayList<>();
        listNote.add(new Note("b", "b"));
        noteAdapter= new NoteAdapter(this, R.layout.item_note, listNote);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);
        */
        /*listNote= new ArrayList<>();
        Query query= Utility.getCollectionReferenceForNotes()
                .whereEqualTo("user")
                .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this, listNote);
        recyclerView.setAdapter(noteAdapter);*/
        //listNote = new ArrayList<>();

        if (currentUser != null){
            String userid = currentUser.getUid();
            String test= "XnFWdZtnq3XZRQf39V5Hem3RsZG3";

            Utility.getCollectionReferenceForNotes()
                    //.whereEqualTo("user_id", userid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                listNote = new ArrayList<>();
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult())
                                {
                                    Note note = documentSnapshot.toObject(Note.class);
                                    listNote.add(note);
                                }
                                loadRecyclerViewAdapter(listNote);
                            }
                        }
                    });
        }
    }
    private void loadRecyclerViewAdapter(ArrayList<Note> notes){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter= new NoteAdapter22(this, R.layout.item_note, listNote);
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();
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