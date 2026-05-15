package com.example.incwadimarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.incwadimarket.databinding.ActivityRegisterScreenBinding

class RegisterScreen : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("IncwadiMarketPrefs", MODE_PRIVATE)

        binding.btnRegister.setOnClickListener {

            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val phone = binding.etPhone.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (name.isEmpty() ||
                surname.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (password != confirmPassword) {

                Toast.makeText(
                    this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // SHOW LOADING

            binding.progressBar.visibility = android.view.View.VISIBLE
            binding.txtLoading.visibility = android.view.View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({

                // SAVE USER

                val editor = sharedPreferences.edit()

                editor.putString("name", name)
                editor.putString("surname", surname)
                editor.putString("phone", phone)
                editor.putString("email", email)
                editor.putString("password", password)

                editor.apply()

                Toast.makeText(
                    this,
                    "User successfully registered",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(
                    Intent(this, LoginScreen::class.java)
                )

                finish()

            }, 8000)

        }

    }
}