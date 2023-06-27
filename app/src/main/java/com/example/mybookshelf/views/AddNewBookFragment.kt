package com.example.mybookshelf.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mybookshelf.BooksApplication
import com.example.mybookshelf.R
import com.example.mybookshelf.databinding.FragmentAddNewBookBinding
import com.example.mybookshelf.models.Book
import com.example.mybookshelf.vievmodels.BooksListsViewModel
import com.example.mybookshelf.vievmodels.BooksListsViewModelFactory

class AddNewBookFragment : Fragment() {

    private var binding: FragmentAddNewBookBinding? = null
    private val navigationArgs: AddNewBookFragmentArgs by navArgs()
    private val booksListViewModel: BooksListsViewModel by viewModels {
        BooksListsViewModelFactory((activity?.application as BooksApplication).repository)
    }
    private lateinit var book: Book

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentAddNewBookBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.itemId
        if (id > 0) {
            booksListViewModel.getBook(id).observe(this.viewLifecycleOwner) { selectedItem ->
                book = selectedItem
                bind(book)
            }
        }
        binding?.apply {
            addBookButton.setOnClickListener { addNewBook() }
            binding?.cancelButton?.setOnClickListener { backToBooksListFragment() }
        }
    }

    private fun backToBooksListFragment() {
        findNavController().navigate(R.id.action_addNewBookFragment_to_booksListFragment)
    }

    private fun isTitleInputValid(): Boolean {
        return booksListViewModel.isTitleInputValid(
            binding?.bookTitleInputText?.text.toString(),
            binding?.bookAuthorInputText?.text.toString(),
            binding?.bookGenreInputText?.text.toString(),
            binding?.bookRankInputText?.text.toString()
        )
    }

    private fun addNewBook() {
        if (isTitleInputValid()) {
            booksListViewModel.addNewBook(
                binding?.bookTitleInputText?.text.toString(),
                binding?.bookAuthorInputText?.text.toString(),
                binding?.bookGenreInputText?.text.toString(),
                binding?.bookRankInputText?.text.toString()
            )
            findNavController().navigate(R.id.action_addNewBookFragment_to_booksListFragment)
        } else {
            Toast.makeText(this.requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bind(book: Book) {
        binding?.apply {
            bookTitleInputText.setText(book.bookTitle, TextView.BufferType.SPANNABLE)
            bookAuthorInputText.setText(book.bookAuthor, TextView.BufferType.SPANNABLE)
            bookGenreInputText.setText(book.bookGenre, TextView.BufferType.SPANNABLE)
            bookRankInputText.setText(book.bookRank.toString(), TextView.BufferType.SPANNABLE)
            addBookButton.setOnClickListener { update() }
        }
    }

    private fun update() {
        if (isTitleInputValid()) {
            booksListViewModel.update(
                id = navigationArgs.itemId,
                bookTitle = binding?.bookTitleInputText?.text.toString(),
                bookAuthor = binding?.bookAuthorInputText?.text.toString(),
                bookGenre = binding?.bookGenreInputText?.text.toString(),
                bookRank = binding?.bookRankInputText?.text.toString()
            )
            findNavController().navigate(
                R.id.action_addNewBookFragment_to_booksListFragment
            )
        } else {
            Toast.makeText(this.requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

}