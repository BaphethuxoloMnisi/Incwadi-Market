package com.example.incwadimarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.incwadimarket.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(
            "IncwadiMarketPrefs",
            MODE_PRIVATE
        )

        binding.btnLogin.setOnClickListener {

            val enteredEmail = binding.etEmail.text.toString()
            val enteredPassword = binding.etPassword.text.toString()

            val savedEmail = sharedPreferences.getString("email", "")
            val savedPassword = sharedPreferences.getString("password", "")

            if (enteredEmail == savedEmail &&
                enteredPassword == savedPassword
            ) {

                // SAVE LOGIN SESSION

                val editor = sharedPreferences.edit()

                editor.putBoolean("isLoggedIn", true)

                editor.apply()

                Toast.makeText(
                    this,
                    "Login Successful",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(this, HomeScreen::class.java)
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

    }
}