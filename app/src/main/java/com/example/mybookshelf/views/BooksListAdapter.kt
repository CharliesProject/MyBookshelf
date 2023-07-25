package com.example.mybookshelf.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mybookshelf.databinding.BookModelItemBinding
import com.example.mybookshelf.models.Book

class BooksListAdapter(private val onItemClicked: (Book) -> Unit) :
    ListAdapter<Book, BooksListAdapter.BooksViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.bookId == newItem.bookId
        }
        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.bookTitle == newItem.bookTitle && oldItem.bookAuthor == newItem.bookAuthor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {

        return BooksViewHolder(
            BookModelItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
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

        private fun convertByteArrayToImage(byteArray: ByteArray): Bitmap? {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }

        fun bind(book: Book) {
            binding.apply {
                bookTitle.text = book.bookTitle
                bookAuthor.text = book.bookAuthor
                bookGenre.text = book.bookGenre
                bookRank.text = book.bookRank.toString()
                bookImage.setImageBitmap(convertByteArrayToImage(book.bookImage))
            }
        }
    }

}
