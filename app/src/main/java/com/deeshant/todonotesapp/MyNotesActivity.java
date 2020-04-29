package com.deeshant.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deeshant.todonotesapp.adapter.NotesAdapter;
import com.deeshant.todonotesapp.clicklisteners.ItemClickListener;
import com.deeshant.todonotesapp.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyNotesActivity extends AppCompatActivity {

    private static final String TAG = "MyNotesActivity";

    String fullName;
    FloatingActionButton fabAddNotes;
   SharedPreferences sharedPreferences;
   RecyclerView recyclerView;
   ArrayList<Notes> notesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_notes);

        binViews();

        setupSharedPreferences();

        getIntentData();

        getSupportActionBar().setTitle(fullName);

        fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpDialogBox();
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstants.FULL_NAME);
        if(TextUtils.isEmpty(fullName)){
            fullName = sharedPreferences.getString(PrefConstants.FULL_NAME,"");
        }
    }

    private void binViews() {
        fabAddNotes = findViewById(R.id.fabAddNotes);
        recyclerView = findViewById(R.id.rvNotes);
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstants.SHARED_PREF_NAME,MODE_PRIVATE);
    }

    private void setUpDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout,null);
        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDesc = view.findViewById(R.id.editTextDesc);
        Button btSubmit = view.findViewById(R.id.btSubmit);

        //Dialog
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        dialog.show();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String desc = editTextDesc.getText().toString();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc)) {
                    Notes notes = new Notes();
                    notes.setTitle(title);
                    notes.setDescription(desc);
                    notesArrayList.add(notes);
                    setupRecyclerView();
                }else {
                    Toast.makeText(MyNotesActivity.this,"Title or Description can't be empty",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });

    }

    private void setupRecyclerView() {

        ItemClickListener itemClickLIstener = new ItemClickListener() {
            @Override
            public void click(Notes notes) {
                Log.d(TAG,notes.getTitle());
                Intent intent = new Intent(MyNotesActivity.this, DetailsActivity.class);
                intent.putExtra(AppConstants.TITLE,notes.getTitle());
                intent.putExtra(AppConstants.DEESCRIPTION,notes.getDescription());
                startActivity(intent);
            }
        };

        NotesAdapter notesAdapter = new NotesAdapter(notesArrayList,itemClickLIstener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notesAdapter);

    }
}
