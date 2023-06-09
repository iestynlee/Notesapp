package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.notesapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    //Loads the structure of the arraylist
    private ArrayList<NoteModal> notes;

    //For the recyclerview
    private NoteAdapter adapter;
    private RecyclerView noteRV;
    private RecyclerView.LayoutManager layoutManager;

    //This is for the button
    public final static int REQUEST_CODE_ADD_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Loads Data
        loadData();
        //Builds the RecyclerView
        buildRecyclerView();

        //Imageview button in bottom right as clickable object
        ImageView addNoteMain = findViewById(R.id.addNote);
        addNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class), //The plus button in bottom right on main page
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });
    }

    //This is the basic RecyclerView set up just constructed here to make the code cleaner
    private void buildRecyclerView(){
        //Loads the note Recycler View on the main activity
        noteRV = findViewById(R.id.notesRecyclerView);
        noteRV.setHasFixedSize(true);

        //The layoutmanager
        layoutManager = new LinearLayoutManager(this);
        //Sets up the adapter
        adapter = new NoteAdapter(notes, MainActivity.this);

        noteRV.setLayoutManager(layoutManager);
        noteRV.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Loads the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This is the id for each item in the options list
        int id = item.getItemId();

        //looks at the create note and starts the activity
        if (id == R.id.create_note) {
            Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}
