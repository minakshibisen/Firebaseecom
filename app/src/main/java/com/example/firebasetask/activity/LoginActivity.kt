package com.example.firebasetask.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetask.MainActivity
import com.example.firebasetask.R
import com.example.firebasetask.databinding.ActivityLoginBinding
import com.example.firebasetask.home.HomeDataActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.textLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeDataActivity::class.java))
                    } else {
                        Toast.makeText(
                            this, "Login Failed : ${task.exception?.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.textSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.textSignupGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credentials = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credentials).addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            Toast.makeText(this, "Sign-In Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, HomeDataActivity::class.java))
                        } else {
                            Toast.makeText(this, "Sign-In Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Sign-In Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}
