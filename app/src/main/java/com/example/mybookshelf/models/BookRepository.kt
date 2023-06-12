package com.example.mybookshelf.models

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class BookRepository(private val database: BookDao) {

    val allBooks: Flow<List<Book>> = database.getAllBooks()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(book: Book) {
        database.insert(book)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(book: Book) {
        database.delete(book)
    }
}