package com.example.firebasetask.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.firebasetask.R
import com.example.firebasetask.databinding.ActivityGetStartedBinding
import com.example.firebasetask.databinding.ActivitySplashBinding

class GetStartedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetStartedBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.textSignUp.setOnClickListener {
                startActivity(Intent(this@GetStartedActivity, SignUpActivity::class.java))
            }
            binding.textLogin.setOnClickListener {
                startActivity(Intent(this@GetStartedActivity, LoginActivity::class.java))
            }
        }
    }
