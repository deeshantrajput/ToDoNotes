package com.deeshant.todonotesapp.clicklisteners

import com.deeshant.todonotesapp.model.Notes

interface ItemClickListener {
    fun click(notes: Notes)
}