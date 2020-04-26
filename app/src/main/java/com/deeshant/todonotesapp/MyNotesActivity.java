package com.deeshant.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyNotesActivity extends AppCompatActivity {

    private static final String TAG = "MyNotesActivity";

    String fullName;
    FloatingActionButton fabAddNotes;
   TextView tvTitle,tvDesc;
   SharedPreferences sharedPreferences;

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
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);

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

                tvDesc.setText(desc);
                tvTitle.setText(title);

                dialog.dismiss();

            }
        });

    }
}
