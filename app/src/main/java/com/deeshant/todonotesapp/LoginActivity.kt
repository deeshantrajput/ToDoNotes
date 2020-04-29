package com.deeshant.todonotesapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity :AppCompatActivity() {

    lateinit var etFullName:EditText
    lateinit var etUserName:EditText

    val TAG = "LoginActivity"

    lateinit var btLogin:Button

    lateinit var sharedPrefrences: SharedPreferences

    lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        setUpSharedPrefrences()
    }

    private fun setUpSharedPrefrences() {
      sharedPrefrences = getSharedPreferences(PrefConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    private fun bindViews() {
        etFullName = findViewById(R.id.editTextFullName)
        etUserName = findViewById(R.id.editTextUserName)
        btLogin = findViewById(R.id.buttonLogin)
    }
}