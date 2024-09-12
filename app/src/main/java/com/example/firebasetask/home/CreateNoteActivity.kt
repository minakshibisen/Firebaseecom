package com.example.firebasetask.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasetask.model.NoteDataClass
import com.example.firebasetask.databinding.ActivityCreateNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize database reference
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.textSaveNote.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val description = binding.edtDescription.text.toString()
            val date = binding.edtDate.text.toString()

            if (title.isEmpty() && description.isEmpty()) {
                Toast.makeText(this, "Fill Both Field", Toast.LENGTH_SHORT).show()
            } else {
                val currentUser = auth.currentUser
                currentUser?.let { user ->
                    //is veriable ka use krke firebase db me jane ke bad ek unique key hum generate kar paynge sabhi note ke liye
                    val noteKey =
                        databaseReference.child("user").child(user.uid).child("note").child("date")
                            .push().key

                    //note item instance
                    val noteItem = NoteDataClass(title, description, date, noteKey ?: "")
                    if (noteKey != null)

                    //add notes to the user note
                        databaseReference.child("user").child(user.uid).child("note").child("date")
                            .child(noteKey).setValue(noteItem).addOnCompleteListener() { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Note save successful", Toast.LENGTH_SHORT)
                                        .show()

                                    finish()
                                } else Toast.makeText(this, "Note save failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                }
            }
        }
    }
}
