package com.example.incwadimarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences("IncwadiMarketPrefs", MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({

            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

            if (isLoggedIn) {

                startActivity(Intent(this, HomeScreen::class.java))

            } else {

                startActivity(Intent(this, WelcomeScreen::class.java))
            }

            finish()

        }, 5000)

    }
}