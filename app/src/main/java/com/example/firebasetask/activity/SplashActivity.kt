package com.example.firebasetask.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.firebasetask.R
import com.example.firebasetask.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        val splashTimeOut = 2500
        Handler(Looper.getMainLooper()).postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashActivity, GetStartedActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
            finish()
        }, splashTimeOut.toLong())
    }


}