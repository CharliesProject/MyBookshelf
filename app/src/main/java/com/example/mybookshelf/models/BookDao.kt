package com.example.mybookshelf.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getAllBooks(): Flow<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)
}