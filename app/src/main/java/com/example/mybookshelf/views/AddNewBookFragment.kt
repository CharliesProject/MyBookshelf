package com.example.mybookshelf.views

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
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
import java.io.ByteArrayOutputStream

class AddNewBookFragment : Fragment() {

    private var binding: FragmentAddNewBookBinding? = null
    private val navigationArgs: AddNewBookFragmentArgs by navArgs()
    private val booksListViewModel: BooksListsViewModel by viewModels {
        BooksListsViewModelFactory((activity?.application as BooksApplication).repository)
    }
    private lateinit var book: Book
    private var selectedImage: Bitmap? = null
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
            bookImage.setOnClickListener { openCamera() }
            addBookButton.setOnClickListener { addNewBook() }
            cancelButton.setOnClickListener { backToBooksListFragment() }
        }
    }

    private fun backToBooksListFragment() {
        findNavController().navigate(R.id.action_addNewBookFragment_to_booksListFragment)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 101)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    val image = data?.extras?.get("data") as? Bitmap
                    image?.let {
                        selectedImage = it
                        binding?.bookImage?.setImageBitmap(selectedImage)
                    }
                }
            }
        }
    }

    private fun convertImageToByteArray(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun convertByteArrayToImage(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun convertImageViewToBitmap(imageView: ImageView?): Bitmap? {
        return when (val drawable = imageView?.drawable) {
            is BitmapDrawable -> drawable.bitmap
            is VectorDrawable -> {
                val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            }
            else -> null
        }
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
                binding?.bookRankInputText?.text.toString(),
                convertImageToByteArray(convertImageViewToBitmap(binding?.bookImage))
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
            bookImage.setImageBitmap(convertByteArrayToImage(book.bookImage))
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
                bookRank = binding?.bookRankInputText?.text.toString(),
                bookImage = convertImageToByteArray(convertImageViewToBitmap(binding?.bookImage))
            )
            findNavController().navigate(
                R.id.action_addNewBookFragment_to_booksListFragment
            )
        } else {
            Toast.makeText(this.requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

}