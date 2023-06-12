package com.example.mybookshelf.vievmodels

import androidx.lifecycle.*
import com.example.mybookshelf.models.Book
import com.example.mybookshelf.models.BookRepository
import kotlinx.coroutines.launch

class BooksListsViewModel(private val repository: BookRepository) : ViewModel() {

    val allBooks: LiveData<List<Book>> = repository.allBooks.asLiveData()

    fun insert(book: Book) = viewModelScope.launch {
        repository.insert(book)
    }

    fun delete(book: Book) = viewModelScope.launch {
        repository.delete(book)
    }

}

class BooksListsViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksListsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BooksListsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}