package com.deeshant.todonotesapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesData")
data class Notes(
        @PrimaryKey(autoGenerate = true)
        val Id:Int? = null,

        @ColumnInfo(name = "title")
        val title:String = "",

        @ColumnInfo(name = "Description")
        val Description: String = "",

        @ColumnInfo(name = "imagePath")
        val imagePath:String = "",

        @ColumnInfo(name = "isTaskCompleted")
        var isTaskCompleted:Boolean = false
)
