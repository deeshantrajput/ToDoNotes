package com.deeshant.todonotesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deeshant.todonotesapp.R;
import com.deeshant.todonotesapp.clicklisteners.ItemClickListener;
import com.deeshant.todonotesapp.model.Notes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    List<Notes> notesList;

    ItemClickListener itemClickLIstener;


    public NotesAdapter(List<Notes> notesList,ItemClickListener itemClickLIstener){
        this.notesList = notesList;
        this.itemClickLIstener = itemClickLIstener;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        final Notes notes = notesList.get(position);
         holder.title.setText(notes.getTitle());
         holder.desc.setText(notes.getDescription());
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 itemClickLIstener.click(notes);
             }
         });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            desc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
