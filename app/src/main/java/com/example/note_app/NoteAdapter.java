package com.example.note_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> implements Filterable {

    Context context;
    private int layoutID;
    private ArrayList<Note> listnotes;
    private ArrayList<Note> listnoteaf;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context, ArrayList<Note> notes) {
        super(options);
        this.context=context;
        this.listnotes=notes;
        this.listnoteaf=listnotes;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position, @NonNull Note note) {
        holder.notetitle.setText(note.getNote_title());
        holder.noteday.setText(note.getNote_day()+"");
        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, note_day.class);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }
    class NoteViewHolder extends RecyclerView.ViewHolder{
        private View itemview;
        public TextView notetitle;
        public TextView noteday;
        public NoteViewHolder(View itemview){
            super(itemview);
            itemview=itemview;
            notetitle=itemview.findViewById(R.id.tv_notetitle);
            noteday=itemview.findViewById(R.id.tv_noteday);
        }
    }
    @Override
    public Filter getFilter() {
        return null;
    }
}
