package com.deeshant.todonotesapp.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

class MyNotesActivity: AppCompatActivity()  {

    var fullName :String? = null
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    var notesArrayList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_notes)
        bindView()
        setUpSharedPrefrences()
        getIntentData()

        gatDatafromDatabase()
        supportActionBar?.title = fullName

        fabAddNotes.setOnClickListener {
            setUpDialogBox()
        }
    }

    private fun gatDatafromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()


    }

    private fun setUpDialogBox() {
        val view: View = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout,null)
        val etTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val etDesc = view.findViewById<EditText>(R.id.editTextDesc)
        val btSubmit = view.findViewById<Button>(R.id.btSubmit)

        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        dialog.show()

        btSubmit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()
                if(title.isNotEmpty() && desc.isNotEmpty()){
                    val notes= Notes(title=title, Description = desc)
                    notesArrayList.add(notes)
                    addNotesToDB(notes)
                    setUpRecyclerView()

                } else {
                    Toast.makeText(this@MyNotesActivity, "Title or Description can't be empty", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }

        }
        )
    }

    private fun addNotesToDB(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao =notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
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