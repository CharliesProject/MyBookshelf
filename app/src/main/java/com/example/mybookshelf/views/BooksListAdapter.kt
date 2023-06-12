package com.example.mybookshelf.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mybookshelf.R
import com.example.mybookshelf.databinding.BookModelItemBinding
import com.example.mybookshelf.models.Book

class BooksListAdapter(private val onItemClicked: (Book) -> Unit) : ListAdapter<Book, BooksListAdapter.BooksViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        return BooksViewHolder(
            BookModelItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {

        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)

    }

    class BooksViewHolder(private var binding: BookModelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.apply {
                bookTitle.text = book.bookTitle
                bookAuthor.text = book.bookAuthor
                bookGenre.text = book.bookGenre
                bookRank.text = book.bookRank.toString()
            }
        }
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.bookTitle == newItem.bookTitle
            }
        }
    }
}
