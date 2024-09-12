package com.example.firebasetask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetask.fragment.NoteFragment
import com.example.firebasetask.databinding.ItemRetriveNoteItemBinding
import com.example.firebasetask.model.NoteDataClass

class NoteAdapter(
    private val notes: List<NoteDataClass>, private val context: Context?
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    interface OnItemClickListener {
        fun deleteItem(noteId: String)
        fun updateItem(noteId: String, title: String, description: String, date: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemRetriveNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = notes[position]
        holder.bind(note)
        holder.binding.imgUpdate.setOnClickListener {
            (context as NoteFragment).updateItem(note.noteId, note.title, note.description, note.date)

        }
        holder.binding.imgDelete.setOnClickListener {
            (context as NoteFragment).deleteItem(note.noteId)
        }

    }
    class NoteViewHolder(val binding: ItemRetriveNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteDataClass) {
            binding.textTitle.text = note.title
            binding.textDescription.text = note.description
            binding.textDate.text = note.date
        }
    }
}