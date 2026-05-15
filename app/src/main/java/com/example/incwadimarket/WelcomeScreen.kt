package com.example.incwadimarket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.incwadimarket.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // REGISTER BUTTON

        binding.btnRegister.setOnClickListener {

            startActivity(
                Intent(this, RegisterScreen::class.java)
            )

        }

        // LOGIN BUTTON

        binding.btnLogin.setOnClickListener {

            startActivity(
                Intent(this, LoginScreen::class.java)
            )

        }

    }
}