package com.deeshant.todonotesapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity(){

    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreferences()
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val isLoogedIn = sharedpreferences.getBoolean(PrefConstants.IS_LOGGED_IN,false)
        if(isLoogedIn){
            val intent = Intent(this@SplashActivity,LoginActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this@SplashActivity,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSharedPreferences() {
        sharedpreferences = getSharedPreferences(PrefConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
}