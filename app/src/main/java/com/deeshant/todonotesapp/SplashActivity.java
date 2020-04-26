package com.deeshant.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupSharedPreferences();
        checkLoginStatus();
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstants.SHARED_PREF_NAME,MODE_PRIVATE);
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean(PrefConstants.IS_LOGGED_IN,false);
        if(isLoggedIn){
            Intent intent = new Intent(SplashActivity.this, MyNotesActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
