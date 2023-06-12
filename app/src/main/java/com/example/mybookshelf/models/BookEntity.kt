package com.example.mybookshelf.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity         //(tableName = "bookshelf")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int?,
    @ColumnInfo(name = "book_title")
    val bookTitle: String,
    @ColumnInfo(name = "book_author")
    val bookAuthor: String,
    @ColumnInfo(name = "book_genre")
    val bookGenre: String,
    @ColumnInfo(name = "book_rank")
    val bookRank: Double,
/*    @ColumnInfo(name = "book_image")
    val bookImage: Int*/
)
