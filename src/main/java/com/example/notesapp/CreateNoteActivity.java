package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.View;
import android.text.TextWatcher;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.notesapp.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateNoteActivity extends AppCompatActivity {

    //Edit Text Variables
    private EditText noteTitleEdt, noteDescEdt;

    //Image Views from layout
    private ImageView imageBack, confirmNote;

    //This is from the Adapter and the arraylist to get information from them
    private NoteAdapter adapter;
    private ArrayList<NoteModal> notes;

    private void saveData() {
        //Shared preferences.
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);

        //Store in shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Create Variable for Gson
        Gson gson = new Gson();

        //Get data from gson to put in array list
        String json = gson.toJson(notes);

        editor.putString("noteslist", json);

        editor.apply();

        adapter.notifyDataSetChanged();

        editor.commit(); //Commit the list

        //After saving data sends toast message
        Toast.makeText(this, "Saved Array List to Shared preferences", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        //To load Arraylist
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();
        String json = sharedPreferences.getString("noteslist", null);

        //Gets type of the ArrayList
        Type type = new TypeToken<ArrayList<NoteModal>>() {}.getType();

        //Collects data from the Gson and uses it in the mainactivity
        notes = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (notes == null) {
            //if arraylist empty creating a new array list.
            notes = new ArrayList<>();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);

        Intent intent = getIntent();
        loadData();

        //Initizilizing variables
        adapter = new NoteAdapter(notes, CreateNoteActivity.this);
        notes = new ArrayList<>();
        noteTitleEdt = findViewById(R.id.inputNoteTitle);
        noteDescEdt = findViewById(R.id.inputNote);

        imageBack = findViewById(R.id.imageBack);
        confirmNote = findViewById(R.id.confirmNote);

        //Checks if its editted in anyway
        noteTitleEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //This is the button to take you back to previous page
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //This is the confirm button
        confirmNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the note title and note
                EditText line1 = findViewById(R.id.inputNoteTitle);
                EditText line2 = findViewById(R.id.inputNote);
                //inserts the item using the function insertitem
                insertItem(line1.getText().toString(), line2.getText().toString());
                adapter.notifyDataSetChanged(); //Notifys it updates the data changed
                saveData();
                onBackPressed();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void insertItem(String line1, String line2) {
        //Adds it to notes
        notes.add(new NoteModal(line1, line2));
        adapter.notifyItemInserted(notes.size());
    }
}