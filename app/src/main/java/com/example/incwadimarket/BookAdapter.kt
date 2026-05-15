package com.example.incwadimarket

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.incwadimarket.databinding.ItemBookBinding

class BookAdapter(
    private var bookList: List<Book>
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>(), Filterable {

    // Original list
    private var fullBookList: List<Book> = ArrayList(bookList)

    inner class BookViewHolder(
        val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        val currentBook = bookList[position]

        holder.binding.txtBookTitle.text = currentBook.title
        holder.binding.txtAuthor.text = currentBook.author
        holder.binding.txtPrice.text = currentBook.price
        holder.binding.txtCondition.text = currentBook.condition

        Glide.with(holder.itemView.context)
            .load(currentBook.imageUri)
            .into(holder.binding.imgBook)

        holder.itemView.setOnClickListener {

            val context = holder.itemView.context
            val intent = Intent(context, BookDetails::class.java)

            intent.putExtra("title", currentBook.title)
            intent.putExtra("author", currentBook.author)
            intent.putExtra("price", currentBook.price)
            intent.putExtra("condition", currentBook.condition)
            intent.putExtra("isbn", currentBook.isbn)
            intent.putExtra("description", currentBook.description)
            intent.putExtra("seller", currentBook.sellerName)
            intent.putExtra("image", currentBook.imageUri)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = bookList.size

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val filteredList = ArrayList<Book>()

                if (constraint.isNullOrEmpty()) {

                    filteredList.addAll(fullBookList)

                } else {

                    val searchText = constraint.toString().lowercase().trim()

                    for (book in fullBookList) {

                        if (
                            book.title.lowercase().contains(searchText) ||
                            book.author.lowercase().contains(searchText)
                        ) {
                            filteredList.add(book)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                bookList = results?.values as ArrayList<Book>
                notifyDataSetChanged()
            }
        }
    }
}