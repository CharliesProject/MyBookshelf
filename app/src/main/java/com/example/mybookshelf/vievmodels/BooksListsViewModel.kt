package com.example.mybookshelf.vievmodels

import androidx.lifecycle.*
import com.example.mybookshelf.models.Book
import com.example.mybookshelf.models.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksListsViewModel(private val repository: BookRepository) : ViewModel() {

    val allBooks: LiveData<List<Book>> = repository.allBooks.asLiveData()

    fun addNewBook(
        bookTitle: String,
        bookAuthor: String,
        bookGenre: String,
        bookRank: String
    ) {
        val newBook = getNewBookEntry(
            bookTitle,
            bookAuthor,
            bookGenre,
            bookRank
        )
        insertBook(newBook)
    }

    private fun getNewBookEntry(
        bookTitle: String,
        bookAuthor: String,
        bookGenre: String,
        bookRank: String
    ): Book {
        return Book(
            bookTitle = bookTitle,
            bookAuthor = bookAuthor,
            bookGenre = bookGenre,
            bookRank = bookRank.toDouble()
        )
    }

    fun update(
        id: Int,
        bookTitle: String,
        bookAuthor: String,
        bookGenre: String,
        bookRank: String
    ) {
        val book = Book(
            bookId = id,
            bookTitle = bookTitle,
            bookAuthor = bookAuthor,
            bookGenre = bookGenre,
            bookRank = bookRank.toDouble()
        )
        viewModelScope.launch(Dispatchers.IO) {
            // TD O: call the DAO method to update a forageable to the database here
            repository.update(book)
        }
    }

    fun isTitleInputValid(
        bookTitle: String,
        bookAuthor: String,
        bookGenre: String,
        bookRank: String
    ): Boolean {
        if (bookTitle.isBlank() || bookAuthor.isBlank() || bookGenre.isBlank() || bookRank.isBlank()) {
            return false
        }
        return true
    }

    private fun insertBook(book: Book) = viewModelScope.launch {
        repository.insert(book)
    }

    fun deleteSelectedBook(book: Book) = viewModelScope.launch {
        repository.delete(book)
    }

    fun getBook(id: Int): LiveData<Book> {
        return repository.getBook(id).asLiveData()
    }

}

class BooksListsViewModelFactory(private val repository: BookRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksListsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BooksListsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}