package com.deeshant.todonotesapp.clicklisteners

import com.deeshant.todonotesapp.db.Notes

interface ItemClickListener {
    fun click(notes: Notes)
    fun checkClick(notes:Notes)
}