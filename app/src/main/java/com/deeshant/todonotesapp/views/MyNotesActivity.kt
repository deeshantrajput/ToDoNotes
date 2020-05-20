package com.deeshant.todonotesapp.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deeshant.todonotesapp.NotesApp
import com.deeshant.todonotesapp.R
import com.deeshant.todonotesapp.adapter.NotesAdapter
import com.deeshant.todonotesapp.clicklisteners.ItemClickListener
import com.deeshant.todonotesapp.db.Notes
import com.deeshant.todonotesapp.utils.AppConstants
import com.deeshant.todonotesapp.utils.PrefConstants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.jar.Manifest

class MyNotesActivity: AppCompatActivity()  {

    var fullName :String? = null
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    var notesArrayList = ArrayList<Notes>()
    val ADD_NOTES_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_notes)
        bindView()
        setUpSharedPrefrences()
        getIntentData()
        gatDatafromDatabase()
        setUpToolBartext()
        setUpClickListener()
        setUpRecyclerView()
    }

    private fun setUpClickListener() {
        fabAddNotes.setOnClickListener {
            setUpDialogBox()
        }
    }



    private fun setUpToolBartext() {
        supportActionBar?.title = Html.fromHtml("<font color='#fff740'>My Notes</font>")
    }



    private fun gatDatafromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesArrayList.addAll(notesDao.getsAll())
    }

    private fun setUpDialogBox() {
        val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
        startActivityForResult(intent,ADD_NOTES_CODE)

    }



    private fun setUpRecyclerView(){

        val itemClickListener = object : ItemClickListener{
            override fun click(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailsActivity::class.java)
                intent.putExtra(AppConstants.TITLE,notes.title)
                intent.putExtra(AppConstants.DEESCRIPTION,notes.Description)
                startActivity(intent)
            }

            override fun checkClick(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao =notesApp.getNotesDb().notesDao()
                notesDao.update(notes)
            }

        }
        val adapter = NotesAdapter(notesArrayList, itemClickListener)
        val layoutManager = LinearLayoutManager(this@MyNotesActivity)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

    }

    private fun getIntentData() {
        val intent = intent
        if(intent.hasExtra(AppConstants.FULL_NAME)){
            fullName = intent.getStringExtra(AppConstants.FULL_NAME)
        }
        if(fullName.isNullOrEmpty()){
            fullName = sharedPreferences.getString(PrefConstants.FULL_NAME,"")
        }
    }

    private fun setUpSharedPrefrences() {
        sharedPreferences = getSharedPreferences(PrefConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    private fun bindView() {
       fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerView = findViewById(R.id.rvNotes)
    }

}