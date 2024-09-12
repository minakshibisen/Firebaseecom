package com.example.firebasetask.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.firebasetask.fragment.AddFragment
import com.example.firebasetask.fragment.ChatFragment
import com.example.firebasetask.fragment.NoteFragment
import com.example.firebasetask.R
import com.example.firebasetask.activity.LoginActivity
import com.example.firebasetask.databinding.ActivityHomeDataBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class HomeDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeDataBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgMenu.setOnClickListener {
            openDrawer()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
        binding.llSignout.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(this, LoginActivity::class.java)
                Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }

        changeFragment(AddFragment())
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        val bottomNavigationItems = mutableListOf(
            CurvedBottomNavigation.Model(R.id.menu_note, "Note", R.drawable.ic_note),
            CurvedBottomNavigation.Model(R.id.menu_add, "Add", R.drawable.ic_add),
            CurvedBottomNavigation.Model(R.id.menu_chat, "Chat", R.drawable.ic_chat),
        )
        binding.bottomBar.apply{
            bottomNavigationItems.forEach { add(it) }
            setOnClickMenuListener {
                Toast.makeText(this@HomeDataActivity, it.title, Toast.LENGTH_SHORT).show()
                when (it.title) {
                    "Note" -> {
                        changeFragment(NoteFragment())
                    }

                    "Add" -> {
                        changeFragment(AddFragment())
                    }

                    else -> {
                        changeFragment(ChatFragment())
                    }
                }
            }
            show(R.id.menu_note)
        }
    }
    private fun openDrawer() {
        binding.drawer.openDrawer(GravityCompat.START)
    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.container.id, fragment).commit()
    }

}
