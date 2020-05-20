package com.deeshant.todonotesapp.views

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.deeshant.todonotesapp.NotesApp
import com.deeshant.todonotesapp.R
import com.deeshant.todonotesapp.db.Notes
import kotlinx.android.synthetic.main.add_notes_dialog_layout.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity: AppCompatActivity() {

    lateinit var etTitle: EditText
    lateinit var etDesc : EditText
   lateinit var btSubmit:Button
    lateinit var dbImage: ImageView
    var notesArrayList = ArrayList<Notes>()
    val REQUEST_CODE_GALLERY =2
    var picturePath = ""
    val MY_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_notes_dialog_layout)
        bindViews()
        setUpClickListener()
    }

    private fun setUpClickListener() {

        dbImage.setOnClickListener {
            checkPersmissionAndGranted()
        }

        btSubmit.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            if(title.isNotEmpty() && desc.isNotEmpty()){
                val notes= Notes(title=title, Description = desc)
                notesArrayList.add(notes)
                addNotesToDB(notes)

            } else {
                Toast.makeText(this@AddNotesActivity, "Title or Description can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPersmissionAndGranted(): Boolean {

        val cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        var listPermission = ArrayList<String>()

        if(cameraPermission!= PackageManager.PERMISSION_GRANTED){
            listPermission.add(android.Manifest.permission.CAMERA)
        }

        if(storagePermission!= PackageManager.PERMISSION_GRANTED){
            listPermission.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(listPermission.isNotEmpty()){
            ActivityCompat.requestPermissions(this, listPermission.toTypedArray(), MY_PERMISSION_CODE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_PERMISSION_CODE->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    setUpDialogBox()
                }
            }
        }
    }

    private fun setUpDialogBox(){
        val view = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_selector,null)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()

        dialog.show()

        val camera= view.findViewById<TextView>(R.id.camera)
        val gallery = view.findViewById<TextView>(R.id.gallery)

        camera.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photoFile: File? = null
            photoFile = createTempImage()
        }

        gallery.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,REQUEST_CODE_GALLERY)
            dialog.hide()
        }

    }

    private fun createTempImage(): File? {
            val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            val filename = "JPEG_$timestamp "
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(filename,"jpeg",storageDir)
    }

    private fun addNotesToDB(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao =notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun bindViews() {
        etTitle = findViewById<EditText>(R.id.editTextTitle)
        etDesc = findViewById<EditText>(R.id.editTextDesc)
        btSubmit = findViewById<Button>(R.id.btSubmit)
        dbImage = findViewById<ImageView>(R.id.dbImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    val selectedImage = data?.data
                    val filepath = arrayOf(MediaStore.Images.Media.DATA)
                    val c = selectedImage?.let { contentResolver.query(it, filepath, null, null,null) }
                    c?.moveToFirst()
                    val columnIndex = c?.getColumnIndex(filepath[0])
                    picturePath = columnIndex?.let { c?.getString(it) }!!
                    c.close()

                    Glide.with(this).load(picturePath).into(dbImage)

                }

            }
        }
    }
}