package com.googlebooksapi.data.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.googlebooksapi.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface Repository {
    //network
    fun searchBooksFlow(query: String): Flow<PagingData<Book>>
    fun getBooksCachedFlow(query: String): Flow<PagingData<Book>>
    suspend fun getBooks(query: String, startIndex: String): List<Book>
    //detail screen
    suspend fun getBookByVolumeId(volumeId: String): Book

    //local db
    suspend fun getBooksFromDb() : PagingSource<Int, Book>
    fun getBooksFromDbFlow() : Flow<List<Book>>
    suspend fun insertBooks(booksList : List<Book>)
    suspend fun deleteBooks() : Unit

}