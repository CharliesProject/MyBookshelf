package com.example.mybookshelf.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mybookshelf.BooksApplication
import com.example.mybookshelf.R
import com.example.mybookshelf.databinding.FragmentBookDetailBinding
import com.example.mybookshelf.models.Book
import com.example.mybookshelf.vievmodels.BooksListsViewModel
import com.example.mybookshelf.vievmodels.BooksListsViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BookDetailFragment : Fragment() {

    private var binding: FragmentBookDetailBinding? = null
    private val navigationArgs: BookDetailFragmentArgs by navArgs()
    private val booksListViewModel: BooksListsViewModel by viewModels {
        BooksListsViewModelFactory((activity?.application as BooksApplication).repository)
    }
    private lateinit var book: Book

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentBookDetailBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        booksListViewModel.getBook(id).observe(this.viewLifecycleOwner) { selectedItem ->
            book = selectedItem
            bind(book)

            binding?.apply {
                deleteBookFab.setOnClickListener { deleteSelectedBookDialog() }
                editBookFab.setOnClickListener { editSelectedItem() }
            }
        }
    }

    private fun convertByteArrayToImage(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun bind(book: Book) {
        binding?.apply {
            bookDetailTitle.text = book.bookTitle
            bookDetailAuthor.text = book.bookAuthor
            bookDetailGenre.text = book.bookGenre
            bookDetailRank.text = book.bookRank.toString()
            bookDetailImage.setImageBitmap(convertByteArrayToImage(book.bookImage))
        }
    }

    private fun deleteSelectedBookDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dialog_delete_book_title))
            .setMessage(resources.getString(R.string.dialog_delete_book_message))
            .setPositiveButton(resources.getString(R.string.dialog_delete_book)) { dialog, _ ->
                dialog.dismiss()
                deleteSelectedItem()
            }
            .setNeutralButton(resources.getString(R.string.dialog_back_to_list)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteSelectedItem() {
        booksListViewModel.deleteSelectedBook(book)
        findNavController().navigateUp()
    }

    private fun editSelectedItem() {
        val action =
            BookDetailFragmentDirections.actionBookDetailFragmentToAddNewBookFragment(book.bookId)
        findNavController().navigate(action)
    }

}
