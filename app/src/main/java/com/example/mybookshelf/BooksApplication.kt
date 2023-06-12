package com.example.mybookshelf

import android.app.Application
import com.example.mybookshelf.models.BookDatabase
import com.example.mybookshelf.models.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BooksApplication  : Application() {
    private val database by lazy { BookDatabase.getDatabase(this) }
    val repository by lazy { BookRepository(database.bookDao()) }
}