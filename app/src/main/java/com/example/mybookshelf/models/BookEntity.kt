package com.example.mybookshelf.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,
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
