package com.example.notesapp;


import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    //Setting Variables
    private LayoutInflater inflater;
    private ArrayList<NoteModal> notes;

    private Context context;

    //For the Adapter
    public NoteAdapter(ArrayList<NoteModal> notes, Context context){
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.note_list,parent,false);
        return new ViewHolder(view); //Sets up the viewholder anf inflates the note list to be used
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position){
        NoteModal modal = notes.get(position);
        holder.NoteTitle.setText(modal.getNoteTitle()); //sets the text for both gettitle and notedescription
        holder.NoteDesc.setText(modal.getNoteDescription());
    }

    @Override //This is the item count
    public int getItemCount(){
        return notes == null ? 0 : notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView NoteTitle, NoteDesc;

        //The note title and note desc to use in other classes
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            NoteTitle = itemView.findViewById(R.id.noteTitle);
            NoteDesc = itemView.findViewById((R.id.noteDescription));
        }
    }
}