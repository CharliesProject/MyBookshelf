package com.example.mybookshelf.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface BookDao {

    @Query("SELECT * FROM book ORDER BY book_title ASC")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE bookId = :id")
    fun getBook(id: Int): Flow<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)
}