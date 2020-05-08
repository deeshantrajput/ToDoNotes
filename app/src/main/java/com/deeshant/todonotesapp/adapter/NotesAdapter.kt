package com.deeshant.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.deeshant.todonotesapp.R
import com.deeshant.todonotesapp.clicklisteners.ItemClickListener
import com.deeshant.todonotesapp.db.Notes

class NotesAdapter(val list:List<Notes>, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent, false)
         return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {

        val notes = list[position]
        val title = notes.title
        val desc = notes.Description
        holder.title.text = title
        holder.desc.text = desc
        holder.checkNotes.isChecked = notes.isTaskCompleted

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                itemClickListener.click(notes)
            }
        })

        holder.checkNotes.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                notes.isTaskCompleted = p1;
                itemClickListener.checkClick(notes)
            }

        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val desc: TextView = itemView.findViewById(R.id.tvDesc)
        val checkNotes: CheckBox = itemView.findViewById(R.id.checkNotes)
        
    }
}