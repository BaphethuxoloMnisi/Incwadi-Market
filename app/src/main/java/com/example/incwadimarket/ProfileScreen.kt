package com.example.incwadimarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.incwadimarket.databinding.ActivityProfileScreenBinding

class ProfileScreen : AppCompatActivity() {

    private lateinit var binding: ActivityProfileScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(
                "IncwadiMarketPrefs",
                MODE_PRIVATE
            )

        // LOAD USER DATA

        binding.etName.setText(
            sharedPreferences.getString("name", "")
        )

        binding.etSurname.setText(
            sharedPreferences.getString("surname", "")
        )

        binding.etPhone.setText(
            sharedPreferences.getString("phone", "")
        )

        binding.etEmail.setText(
            sharedPreferences.getString("email", "")
        )

        // SAVE CHANGES

        binding.btnSave.setOnClickListener {

            val editor =
                sharedPreferences.edit()

            editor.putString(
                "name",
                binding.etName.text.toString()
            )

            editor.putString(
                "surname",
                binding.etSurname.text.toString()
            )

            editor.putString(
                "phone",
                binding.etPhone.text.toString()
            )

            editor.putString(
                "email",
                binding.etEmail.text.toString()
            )

            editor.apply()

            Toast.makeText(
                this,
                "Profile updated successfully",
                Toast.LENGTH_SHORT
            ).show()

        }

        // LOGOUT

        binding.btnLogout.setOnClickListener {

            val editor =
                sharedPreferences.edit()

            editor.putBoolean(
                "isLoggedIn",
                false
            )

            editor.apply()

            Toast.makeText(
                this,
                "Logged out successfully",
                Toast.LENGTH_SHORT
            ).show()

            val intent =
                Intent(
                    this,
                    WelcomeScreen::class.java
                )

            startActivity(intent)

            finish()

        }

        // =========================
        // BOTTOM NAVIGATION (DECLARED VARIABLES)
        // =========================

        val navHome = binding.navHome
        val navMessages = binding.navMessages
        val navProfile = binding.navProfile

        navHome.setOnClickListener {

            // Go back to HomeScreen and clear stack
            val intent = Intent(this, HomeScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        navMessages.setOnClickListener {

            val intent = Intent(this, MessagesScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        navProfile.setOnClickListener {

            val intent = Intent(this, ProfileScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

}