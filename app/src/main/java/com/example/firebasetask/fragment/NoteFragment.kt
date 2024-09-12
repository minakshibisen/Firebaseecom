package com.example.firebasetask.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetask.adapter.NoteAdapter
import com.example.firebasetask.databinding.FragmentNoteBinding
import com.example.firebasetask.databinding.ItemUpdateNoteBinding
import com.example.firebasetask.home.CreateNoteActivity
import com.example.firebasetask.model.NoteDataClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NoteFragment : Fragment(), NoteAdapter.OnItemClickListener {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerview: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        recyclerview = binding.recyclerview
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val noteRefrence =
                databaseReference.child("user").child(user.uid).child("note").child("date")
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
                    val adapter = NoteAdapter(noteList, requireContext())
                    recyclerview.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }

            )
        }

        binding.imgCreateNote.setOnClickListener {
            startActivity(Intent(context, CreateNoteActivity::class.java))
        }

        return binding.root
    }

    override fun deleteItem(noteId: String) {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") { _, _ ->
            Toast.makeText(
                context,
                "Successfully removed ",
                Toast.LENGTH_SHORT
            )
                .show()
            deleteitems(noteId)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete")
        builder.setMessage("Are you sure to remove ?")
        builder.create().show()
    }
    private fun deleteitems(noteId: String) {
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val noteRefrence =
                databaseReference.child("user").child(user.uid).child("note").child("date")
            noteRefrence.child(noteId).removeValue()
        }
    }
    override fun updateItem(
        noteId: String,
        title: String,
        description: String,
        date: String
    ) {

        val dialogBinding = ItemUpdateNoteBinding.inflate(LayoutInflater.from(context))

        val dialog = context?.let {
            AlertDialog.Builder(it).setView(dialogBinding.root)
                .setTitle("Update Note")
                .setPositiveButton("update") { dialog, _ ->
                    val newTitle = dialogBinding.edtTitle.text.toString()
                    val newDescription = dialogBinding.edtDescription.text.toString()
                    val newDate = dialogBinding.edtDate.text.toString()

                    setUpdateDialog(noteId, newTitle, newDescription, newDate)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
        }
        dialogBinding.edtTitle.setText(title)
        dialogBinding.edtDescription.setText(description)
        dialogBinding.edtDate.setText(date)

        dialog?.show()
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
                databaseReference.child("user").child(user.uid).child("note").child("date")
            val updateNote = NoteDataClass(newTitle, newDescription, newDate, noteId)

            noteRefrence.child(noteId).setValue(updateNote)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Note Update successful", Toast.LENGTH_SHORT)
                            .show()

                    } else Toast.makeText(context, "Note Update failed", Toast.LENGTH_SHORT)
                        .show()

                }
        }
    }
    /*   private fun searchBottomSheet() {
           val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
           val bottomSheetBinding = ItemNoteLayoutBinding.inflate(LayoutInflater.from(context))
           bottomSheetDialog.setContentView(bottomSheetBinding.root)

           bottomSheetBinding.openNote.setOnClickListener {
               startActivity(Intent(context, OpenNoteActivity::class.java))
           }
           bottomSheetDialog.show()
       }*/
}