package com.example.incwadimarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.incwadimarket.databinding.ActivityMessagesScreenBinding

class MessagesScreen : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var chatList: ArrayList<Chat>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityMessagesScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(
                "IncwadiMarketPrefs",
                MODE_PRIVATE
            )

        loadConversations()

        binding.recyclerChats.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerChats.adapter =
            ChatAdapter(chatList)

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

    private fun loadConversations() {

        chatList = arrayListOf()

        val conversations =
            sharedPreferences.getStringSet(
                "conversations",
                mutableSetOf()
            ) ?: mutableSetOf()

        for (user in conversations) {

            chatList.add(

                Chat(
                    user,
                    "Tap to continue chatting",
                    "",
                    "",
                    "Online"
                )

            )

        }

    }

}