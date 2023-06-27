package com.example.mybookshelf.models

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class BookRepository(private val database: BookDao) {

    val allBooks: Flow<List<Book>> = database.getAllBooks()

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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(book: Book) {
        database.update(book)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getBook(id: Int): Flow<Book> {
        return database.getBook(id)
    }

}