package com.deeshant.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextFullName, editTextUserName;
    Button buttonLogin;

    private static final String TAG = "LoginActivity";

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();

        setUpSharedPreferences();

        View.OnClickListener clickAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String full_name = editTextFullName.getText().toString();
                String user_name = editTextUserName.getText().toString();

                if(!TextUtils.isEmpty(full_name) && !TextUtils.isEmpty(user_name)) {

                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra(AppConstants.FULL_NAME , full_name);
                    startActivity(intent);

                    saveLoginStatus();

                    saveFullName(full_name);

                }else {
                    Toast.makeText(LoginActivity.this,"Full name and user name can't be empty", Toast.LENGTH_LONG).show();
                }
            }
        };

        buttonLogin.setOnClickListener(clickAction);

    }

    private void saveFullName(String full_name) {
        editor = sharedPreferences.edit();
        editor.putString(PrefConstants.FULL_NAME,full_name);
        editor.apply();
    }

    private void saveLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefConstants.IS_LOGGED_IN,true);
        editor.apply();
    }

    private void setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstants.SHARED_PREF_NAME,MODE_PRIVATE);
    }

    private void bindViews() {
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextUserName = findViewById(R.id.editTextUserName);
    }
}
