package com.example.incwadimarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.incwadimarket.databinding.ActivityBrowseBooksBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BrowseBooks : AppCompatActivity() {

    private lateinit var binding: ActivityBrowseBooksBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bookList: ArrayList<Book>

    // ADD THIS
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityBrowseBooksBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(
                "IncwadiMarketPrefs",
                MODE_PRIVATE
            )

        // LOAD BOOKS
        loadBooks()

        // RECYCLERVIEW
        binding.recyclerBooks.layoutManager =
            LinearLayoutManager(this)

        // CREATE ADAPTER
        bookAdapter = BookAdapter(bookList)

        // SET ADAPTER
        binding.recyclerBooks.adapter = bookAdapter

        // SEARCH FUNCTION
        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                // FILTER BOOKS
                bookAdapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        // BOTTOM NAVIGATION

        binding.navHome.setOnClickListener {

            startActivity(
                Intent(this, HomeScreen::class.java)
            )

        }

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

    private fun loadBooks() {

        val gson = Gson()

        val json =
            sharedPreferences.getString(
                "books",
                null
            )

        bookList = try {

            if (!json.isNullOrEmpty()) {

                val type =
                    object : TypeToken<ArrayList<Book>>() {}.type

                gson.fromJson(json, type)
                    ?: arrayListOf()

            } else {

                arrayListOf()

            }

        } catch (e: Exception) {

            e.printStackTrace()

            arrayListOf()

        }
    }
}