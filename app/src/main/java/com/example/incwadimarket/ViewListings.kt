package com.example.incwadimarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.incwadimarket.databinding.ActivityViewListingsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ViewListings : AppCompatActivity() {

    private lateinit var binding: ActivityViewListingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var bookList: ArrayList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityViewListingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(
                "IncwadiMarketPrefs",
                MODE_PRIVATE
            )

        loadBooks()

        binding.recyclerListings.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerListings.adapter =
            BookAdapter(bookList)

        binding.btnAddListing.setOnClickListener {

            startActivity(
                Intent(this, SellBook::class.java)
            )

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

    private fun loadBooks() {

        val gson = Gson()

        val json = sharedPreferences.getString("books", null)

        bookList = try {

            if (!json.isNullOrEmpty()) {
                val type = object : TypeToken<ArrayList<Book>>() {}.type
                gson.fromJson(json, type) ?: arrayListOf()
            } else {
                arrayListOf()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            arrayListOf() // fallback instead of crashing
        }
    }

}