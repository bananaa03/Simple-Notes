package com.example.note_app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.Timestamp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class note_day extends AppCompatActivity {

    private ImageButton buttonSetting, btnShare;
    EditText edtnotetitle, edtnotecontent;
    ImageButton buttonBack, btnSaveNote;
    boolean isEditMode = false;
    String title,content,docId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_day);
        edtnotetitle = (EditText) findViewById(R.id.edt_note_title);
        edtnotecontent= (EditText) findViewById(R.id.edt_note_content);

        buttonSetting = (ImageButton) findViewById(R.id.ImageButtonSetting);
        btnShare = (ImageButton) findViewById(R.id.imgbtn_share);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSetting();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteTitle = edtnotetitle.getText().toString();
                String noteContent = edtnotecontent.getText().toString();
                String fullNote = noteTitle+"Chủ nhật"+"\n\n"+ noteContent+"Hi";

                //Tạo intent chia sẻ nội dung
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, fullNote);

                startActivity(Intent.createChooser(shareIntent,"Chia sẻ nội dung ghi chú"));
            }
        });

        buttonBack = (ImageButton) findViewById(R.id.ImageButtonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBack();
            }
        });

        //Save note
        btnSaveNote = (ImageButton) findViewById(R.id.iBt_Save);
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });


    }
    public void openSetting()
    {
        Intent intent = new Intent(this, note_day.class);
        startActivity(intent);
    }

    public void openBack(){
        Intent intent = new Intent(note_day.this, day_main.class);
        startActivity(intent);
    }

    public void saveNote(){
        String noteTitle = edtnotetitle.getText().toString();
        String noteContent = edtnotecontent.getText().toString();
        if(noteTitle == null || noteTitle.isEmpty()){
            edtnotetitle.setError("Hãy nhập chủ đề");
            return;
        }

        Note note = new Note();
        note.setNote_title(noteTitle);
        note.setNote_content(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFireBase(note);
    }

    public void saveNoteToFireBase(Note note){
        DocumentReference documentReference;
        if(isEditMode){
            //update the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        }else{
            //create new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //note is added
                    Utility.showToast(note_day.this,"Note added successfully");
                    finish();
                }else{
                    Utility.showToast(note_day.this,"Failed while adding note");
                }
            }
        });
    }
}
