package com.example.incwadimarket

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.incwadimarket.databinding.ActivityChatScreenBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatScreen : AppCompatActivity() {

    private lateinit var binding: ActivityChatScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var messageList: ArrayList<Message>

    private lateinit var currentUser: String
    private lateinit var otherUser: String

    private lateinit var chatKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(
                "IncwadiMarketPrefs",
                MODE_PRIVATE
            )

        // CURRENT LOGGED USER

        currentUser =
            sharedPreferences.getString(
                "name",
                ""
            ) ?: ""

        // PERSON WE ARE CHATTING WITH

        otherUser =
            intent.getStringExtra("name") ?: "Unknown"

        binding.txtUserName.text = otherUser

        // UNIQUE CHAT KEY

        chatKey =
            if (currentUser < otherUser) {

                "${currentUser}_$otherUser"

            } else {

                "${otherUser}_$currentUser"

            }

        loadMessages()

        binding.recyclerMessages.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerMessages.adapter =
            MessageAdapter(
                messageList,
                currentUser
            )

        // SEND MESSAGE

        binding.btnSend.setOnClickListener {

            val text =
                binding.etMessage.text.toString()

            if (text.isNotEmpty()) {

                val currentTime =
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(Date())

                val message = Message(

                    currentUser,
                    otherUser,
                    text,
                    currentTime

                )

                messageList.add(message)

                saveMessages()

                binding.recyclerMessages.adapter =
                    MessageAdapter(
                        messageList,
                        currentUser
                    )

                binding.recyclerMessages.scrollToPosition(
                    messageList.size - 1
                )

                binding.etMessage.text.clear()

                saveConversation()

            }

        }

    }

    // SAVE CONVERSATION USERS

    private fun saveConversation() {

        val existing =
            sharedPreferences.getStringSet(
                "conversations",
                mutableSetOf()
            ) ?: mutableSetOf()

        existing.add(otherUser)

        sharedPreferences.edit()
            .putStringSet(
                "conversations",
                existing
            )
            .apply()

    }

    // SAVE MESSAGES

    private fun saveMessages() {

        val gson = Gson()

        val json =
            gson.toJson(messageList)

        sharedPreferences.edit()
            .putString(chatKey, json)
            .apply()

    }

    // LOAD MESSAGES

    private fun loadMessages() {

        val gson = Gson()

        val json =
            sharedPreferences.getString(
                chatKey,
                null
            )

        val type =
            object : TypeToken<ArrayList<Message>>() {}.type

        messageList =
            if (!json.isNullOrEmpty()) {

                gson.fromJson(json, type)

            } else {

                arrayListOf()

            }

    }

}