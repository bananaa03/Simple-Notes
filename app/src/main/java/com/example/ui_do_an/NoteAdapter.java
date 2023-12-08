package com.example.ui_do_an;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Adapter cho Recycle View để hiển thị note ra trang day_main.java và night_main.java
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private ArrayList<Note> listnotes;
    private Context context;

    public NoteAdapter(ArrayList<Note> notes, Context context){
        this.listnotes=notes;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.item_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = listnotes.get(position);

        holder.notetitle.setText(note.getNote_title());
        holder.noteday.setText(note.getNote_day()+"");
    }

    @Override
    public int getItemCount() {
        return listnotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView notetitle;
        public TextView noteday;
        public ViewHolder(View itemview){
            super(itemview);
            itemview=itemview;
            notetitle=itemview.findViewById(R.id.tv_notetitle);
            noteday=itemview.findViewById(R.id.tv_noteday);
        }
    }
}
