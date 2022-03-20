package com.googlebooksapi.domain.database

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.googlebooksapi.domain.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertBook(book: Book)

    @Insert(onConflict = REPLACE)
    suspend fun insertBooks(booksList: List<Book>)

    @Update(onConflict = REPLACE)
    suspend fun updateBook(book: Book)

    @Delete()
    suspend fun deleteBook(book: Book)

    @Query("DELETE from book")
    suspend fun deleteBooks()

    @Query("SELECT * FROM book")
    fun getBooksFromDb() : PagingSource<Int, Book>

    @Query("SELECT * FROM book")
    fun getBooksFromDbFlow() : Flow<List<Book>>

    @Query("SELECT COUNT(*) FROM book")
    suspend fun getBooksCountFromDb() : Int

    @Query("SELECT * FROM book LIMIT 10")
    fun getBooksLimit() : Flow<List<Book>>

    @Query("SELECT * FROM book WHERE title LIKE '%' || :query || '%'")
    fun getBooksLike(query: String) : Flow<List<Book>>

}