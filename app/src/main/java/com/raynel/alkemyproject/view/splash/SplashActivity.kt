package com.raynel.alkemyproject.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.raynel.alkemyproject.databinding.ActivitySplashBinding
import com.raynel.alkemyproject.view.principalActivity.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.myLooper()!!)

        handler.postDelayed({
            startActivity(Intent(
                this,
                MainActivity::class.java
            ))
        }, 5000)

    }

}