package com.deeshant.todonotesapp.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.deeshant.todonotesapp.R
import com.deeshant.todonotesapp.utils.AppConstants
import com.deeshant.todonotesapp.utils.PrefConstants

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

        val clickaction = object: View.OnClickListener {
            override fun onClick(p0: View?) {
                val full_name = etFullName.text.toString()
                val user_name = etUserName.text.toString()

                if(full_name.isNotEmpty() && user_name.isNotEmpty()){
                    val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstants.FULL_NAME,full_name)
                    startActivity(intent)

                    saveLoginStatus()
                    saveFullname(full_name)

                } else {
                    Toast.makeText(this@LoginActivity, "Full name and user name can't be empty", Toast.LENGTH_LONG).show()
                }
            }

        }

        btLogin.setOnClickListener(clickaction)
    }

    private fun saveFullname(fullName: String) {
        editor = sharedPrefrences.edit()
        editor.putString(PrefConstants.FULL_NAME,fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPrefrences.edit()
        editor.putBoolean(PrefConstants.IS_LOGGED_IN,true)
        editor.apply()
    }
}