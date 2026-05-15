package com.example.incwadimarket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.incwadimarket.databinding.ActivityBookDetailsBinding
import com.bumptech.glide.Glide

class BookDetails : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val price = intent.getStringExtra("price")
        val condition = intent.getStringExtra("condition")
        val isbn = intent.getStringExtra("isbn")
        val description = intent.getStringExtra("description")
        val imageUri = intent.getStringExtra("image")
        val seller = intent.getStringExtra("seller")

        binding.txtTitle.text = title
        binding.txtAuthor.text = author
        binding.txtPrice.text = price
        binding.txtCondition.text = condition
        binding.txtISBN.text = "ISBN: $isbn"
        binding.txtDescription.text = description
        binding.txtSeller.text = "Seller: $seller"

        if (!imageUri.isNullOrEmpty()) {

            try {
                Glide.with(binding.imgBook.context)
                    .load(imageUri)
                    .into(binding.imgBook)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.btnMessageBuyer.setOnClickListener {
            val intent = Intent(this, ChatScreen::class.java)
            intent.putExtra("name", seller)
            startActivity(intent)
        }
    }
}