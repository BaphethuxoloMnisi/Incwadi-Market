package com.example.incwadimarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.incwadimarket.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(
            "IncwadiMarketPrefs",
            MODE_PRIVATE
        )

        // GET USER NAME

        val userName = sharedPreferences.getString("name", "User")

        binding.txtWelcome.text = "Welcome $userName"

        // CARD NAVIGATION

        binding.cardBrowseBooks.setOnClickListener {

            startActivity(
                Intent(this, BrowseBooks::class.java)
            )

        }

        binding.cardViewListings.setOnClickListener {

            startActivity(
                Intent(this, ViewListings::class.java)
            )

        }

        binding.cardBrowseBooks.setOnClickListener {
            Toast.makeText(this, "Home works", Toast.LENGTH_SHORT).show()
        }

        binding.cardSellBook.setOnClickListener {

            startActivity(
                Intent(this, SellBook::class.java)
            )

        }

        // BOTTOM NAVIGATION

        binding.navMessages.setOnClickListener {

            startActivity(
                Intent(this, MessagesScreen::class.java)
            )

        }

        binding.navProfile.setOnClickListener {

            startActivity(
                Intent(this, ProfileScreen::class.java)
            )

        }

    }
}