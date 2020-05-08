package com.deeshant.todonotesapp.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface NotesDao {

    @Query("Select * from notesData")
    fun getsAll():List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun update(notes: Notes)

    @Delete
    fun delete(notes: Notes)
}