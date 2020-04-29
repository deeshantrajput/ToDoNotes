package com.deeshant.todonotesapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity(){

    lateinit var tvTitle:TextView
    lateinit var  tvDesc:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        bindViews()
        setUpIntentData()
    }

    private fun setUpIntentData() {
        val intent = intent
        tvTitle.text = intent.getStringExtra(AppConstants.TITLE)
        tvDesc.text = intent.getStringExtra(AppConstants.DEESCRIPTION)
    }

    private fun bindViews() {
        tvTitle = findViewById(R.id.tvTitle)
        tvDesc = findViewById(R.id.tvDesc)
    }
}
