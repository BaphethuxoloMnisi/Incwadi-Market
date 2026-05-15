package com.example.incwadimarket

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.incwadimarket.databinding.ActivitySellBookBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class SellBook : AppCompatActivity() {

    private lateinit var binding: ActivitySellBookBinding
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var bookList: ArrayList<Book>

    private var selectedImageUri: Uri? = null

    companion object {

        const val IMAGE_REQUEST_CODE = 100

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySellBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(
                "IncwadiMarketPrefs",
                MODE_PRIVATE
            )

        loadBooks()

        // UPLOAD IMAGE

        binding.btnUploadImage.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            startActivityForResult(
                intent,
                IMAGE_REQUEST_CODE
            )

        }

        // LIST BOOK

        binding.btnListBook.setOnClickListener {

            if (selectedImageUri == null) {

                Toast.makeText(
                    this,
                    "Please upload a book image",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener

            }

            val sellerName =
                sharedPreferences.getString(
                    "name",
                    "Unknown Seller"
                ) ?: "Unknown Seller"

            val book = Book(

                binding.etTitle.text.toString(),
                binding.etAuthor.text.toString(),
                "R${binding.etPrice.text}",
                binding.etCondition.text.toString(),
                binding.etISBN.text.toString(),
                binding.etDescription.text.toString(),
                sellerName,
                copyImageToInternalStorage(selectedImageUri!!)

            )

            bookList.add(book)

            saveBooks()

            Toast.makeText(
                this,
                "Book listed successfully",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(this, ViewListings::class.java)
            )

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

    // IMAGE RESULT

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == IMAGE_REQUEST_CODE &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {

            selectedImageUri = data.data

            binding.imgBookPreview.setImageURI(
                selectedImageUri
            )

        }

    }

    // SAVE BOOKS

    private fun saveBooks() {

        val gson = Gson()

        val json =
            gson.toJson(bookList)

        sharedPreferences.edit()
            .putString("books", json)
            .apply()

    }

    // LOAD BOOKS

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

    private fun copyImageToInternalStorage(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(filesDir, "book_${System.currentTimeMillis()}.jpg")
        val outputStream = file.outputStream()

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return file.absolutePath
    }



}