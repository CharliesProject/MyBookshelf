package com.example.mybookshelf.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybookshelf.BooksApplication
import com.example.mybookshelf.R
import com.example.mybookshelf.databinding.FragmentBooksListBinding
import com.example.mybookshelf.models.Book
import com.example.mybookshelf.vievmodels.BooksListsViewModel
import com.example.mybookshelf.vievmodels.BooksListsViewModelFactory

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
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BooksListAdapter {}
        booksListViewModel.allBooks.observe(this.viewLifecycleOwner) { books ->
            books.let { adapter.submitList(it) }
        }
        binding?.apply{
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }
    }


}