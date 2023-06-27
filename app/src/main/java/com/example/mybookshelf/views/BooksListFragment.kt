package com.example.mybookshelf.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybookshelf.BooksApplication
import com.example.mybookshelf.R
import com.example.mybookshelf.databinding.FragmentBooksListBinding
import com.example.mybookshelf.vievmodels.BooksListsViewModel
import com.example.mybookshelf.vievmodels.BooksListsViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BooksListFragment : Fragment() {

    private var binding: FragmentBooksListBinding? = null

    private val booksListViewModel: BooksListsViewModel by viewModels {
        BooksListsViewModelFactory((activity?.application as BooksApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentBooksListBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BooksListAdapter {
            val action =
                BooksListFragmentDirections.actionBooksListFragmentToBookDetailFragment(it.bookId)
            this.findNavController().navigate(action)
        }
        booksListViewModel.allBooks.observe(this.viewLifecycleOwner) { items ->
            items.let { adapter.submitList(it) }
        }
        binding?.apply {
            recyclerView.adapter = adapter
            //    recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
            addNewBookFab.setOnClickListener { chooseAddOptionsDialog() }
        }
    }

    private fun chooseAddOptionsDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dialog_title))
            .setMessage(resources.getString(R.string.dialog_message))
            .setPositiveButton(resources.getString(R.string.dialog_isbn_scanner)) { dialog, _ ->
                dialog.dismiss()
                addNewBookWithIsbnScanner()
            }
            .setNeutralButton(resources.getString(R.string.dialog_back_to_list)) { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.dialog_add_manually)) { dialog, _ ->
                dialog.dismiss()
                addNewBookManually()
            }
            .show()
    }

    private fun addNewBookWithIsbnScanner() {}

    private fun addNewBookManually() {
        findNavController().navigate(R.id.action_booksListFragment_to_addNewBookFragment)
    }

}