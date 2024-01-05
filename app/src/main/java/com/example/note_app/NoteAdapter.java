package com.example.note_app;

import android.app.Activity;
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

import java.util.ArrayList;

//Adapter cho Recycle View để hiển thị note ra trang main.java
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements Filterable {
    private ArrayList<Note> listnotes;
    private int layoutID;
    private Activity context;
    private ArrayList<Note> listnoteaf;

    public NoteAdapter(Activity context, int layoutID, ArrayList<Note> notes){
        this.listnotes=notes;
        this.context=context;
        this.layoutID=layoutID;
        listnoteaf= new ArrayList<>(listnotes);
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
        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, note_take.class);
            intent.putExtra("NOTE_TITLE", note.getNote_title().toString());
            intent.putExtra("NOTE_CONTENT", note.getNote_content().toString());
            intent.putExtra("NOTE_DATE", note.getNote_day().toString());
            context.startActivity(intent);
        });
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
    @Override
    public Filter getFilter(){
        return listnotesFilter;
    }
    private Filter listnotesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Note> filterList = new ArrayList<>();

            if(constraint==null || constraint.length() == 0){
                filterList.addAll(listnoteaf);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Note item : listnoteaf){
                    if(item.getNote_title().toLowerCase().contains(filterPattern)){
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values= filterList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listnotes.clear();
            listnotes.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
