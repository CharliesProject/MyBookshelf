package com.example.mybookshelf.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mybookshelf.R
import com.example.mybookshelf.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {

    private var binding: FragmentMainMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentMainMenuBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            currentlyReadButton.setOnClickListener { goToCurrentlyReadList() }
            readButton.setOnClickListener { goToReadList() }
            myBooksButton.setOnClickListener { goToMyBooksList() }
            libraryBooksButton.setOnClickListener { goToLibraryBooksList() }
            borrowedBooksButton.setOnClickListener { goToBorrowedBooksList()}
            wishlistBooksButton.setOnClickListener { goToWishlistBooksList() }
        }
    }

    private fun goToCurrentlyReadList() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_booksListFragment)
    }

    private fun goToReadList() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_booksListFragment)
    }

    private fun goToMyBooksList() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_booksListFragment)
    }

    private fun goToLibraryBooksList() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_booksListFragment)
    }

    private fun goToBorrowedBooksList() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_booksListFragment)
    }

    private fun goToWishlistBooksList() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_booksListFragment)
    }


    /**    private fun goToCurrentlyReadFragment() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_currentlyReadFragment)
    }

    private fun goToReadFragment() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_readFragment)
    }

    private fun goToMyBooksFragment() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_myBooksFragment)
    }

    private fun goToLibraryBooksFragment() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_libraryBooksFragment)
    }

    private fun goToBorrowedBooksFragment() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_borrowedBooksFragment)
    }

    private fun goToWishlistBooksFragment() {
        findNavController().navigate(R.id.action_mainMenuFragment_to_wishlistBooksFragment)
    }*/

}