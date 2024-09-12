package com.example.firebasetask.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetask.adapter.NoteAdapter
import com.example.firebasetask.databinding.ActivityOpenNoteBinding
import com.example.firebasetask.databinding.ItemUpdateNoteBinding
import com.example.firebasetask.model.NoteDataClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OpenNoteActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener {
    private lateinit var binding: ActivityOpenNoteBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerview = binding.recyclerview
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val noteRefrence =
                databaseReference.child("user").child(user.uid).child("notes").child("date")
            noteRefrence.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val noteList = mutableListOf<NoteDataClass>()
                    for (noteSnapshot in snapshot.children) {
                        val note = noteSnapshot.getValue(NoteDataClass::class.java)
                        note?.let {
                            noteList.add(it)
                        }
                    }
                    noteList.reverse()
                    val adapter = NoteAdapter(noteList, this@OpenNoteActivity)
                    recyclerview.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }

            )
        }
    }
    override fun deleteItem(noteId: String) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { _, _ ->
            Toast.makeText(
                this,
                "Successfully removed ",
                Toast.LENGTH_SHORT)
                .show()
          deleteitems(noteId)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete")
        builder.setMessage("Are you sure to remove ?")
        builder.create().show()
    }

    private fun deleteitems( noteId:String) {
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val noteRefrence =
                databaseReference.child("user").child(user.uid).child("notes").child("date")
            noteRefrence.child(noteId).removeValue()
        }
    }


  /*  override fun deleteItem(noteId: String) {

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val noteRefrence =
                databaseReference.child("user").child(user.uid).child("notes").child("date")
            noteRefrence.child(noteId).removeValue()
        }
    }*/

    override fun updateItem(
        noteId: String,
        currentTitle: String,
        currentDescription: String,
      currentDate: String
    ) {

        val dialogBinding = ItemUpdateNoteBinding.inflate(LayoutInflater.from(this))

        val dialog = AlertDialog.Builder(this).setView(dialogBinding.root)
            .setTitle("Update Note")
            .setPositiveButton("update") { dialog, _ ->
                val newTitle = dialogBinding.edtTitle.text.toString()
                val newDescription = dialogBinding.edtDescription.text.toString()
                val newDate = dialogBinding.edtDate.text.toString()

                setUpdateDialog(noteId, newTitle, newDescription, newDate)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
            .create()
        dialogBinding.edtTitle.setText(currentTitle)
        dialogBinding.edtDescription.setText(currentDescription)
        dialogBinding.edtDate.setText(currentDate)

        dialog.show()
    }

    private fun setUpdateDialog(
        noteId: String,
        newTitle: String,
        newDescription: String,
        newDate: String
    ) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val noteRefrence =
                databaseReference.child("user").child(user.uid).child("notes").child("date")
            val updateNote = NoteDataClass(newTitle, newDescription, newDate, noteId)

            noteRefrence.child(noteId).setValue(updateNote)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Note Update successful", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else Toast.makeText(this, "Note Update failed", Toast.LENGTH_SHORT)
                        .show()

                }
        }
    }
}