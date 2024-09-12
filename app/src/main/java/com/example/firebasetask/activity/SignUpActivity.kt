package com.example.firebasetask.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasetask.R
import com.example.firebasetask.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //initialize auth firebase
        auth = FirebaseAuth.getInstance()

        binding.textLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.textSignUp.setOnClickListener {

            //get text from edt text
            val email = binding.edtEmail.text.toString()
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            val lastname = binding.edtLastName.text.toString()
            val confirmpassword = binding.edtConfirmPass.text.toString()
            val mobile = binding.edtMobileNo.text.toString()
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || lastname.isEmpty() || confirmpassword.isEmpty() || mobile.isEmpty()) {
                Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show()
            } else if (password != confirmpassword) {
                Toast.makeText(this, "Repeat Password Must Be Same", Toast.LENGTH_SHORT).show()
            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(
                            this,
                            "Registration Failed : ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }

        }

    }

}
