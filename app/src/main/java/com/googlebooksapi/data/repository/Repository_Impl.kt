package com.googlebooksapi.data.repository

import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.data.*
import com.googlebooksapi.data.dto.toBook
import com.googlebooksapi.data.dto.toBooksResponse
import com.googlebooksapi.ui.presentation.paging3search.BooksPagingSource
import com.googlebooksapi.utils.Constants
import androidx.paging.*
import com.googlebooksapi.domain.database.Database
import com.googlebooksapi.ui.presentation.paging3cache.BooksRemoteMediator
import kotlinx.coroutines.flow.Flow

class Repository_Impl(
    private val booksApiService: ApiService,
    private val database: Database,
) : Repository {

    private val booksDao = database.booksDao()

    override suspend fun getBooksFromDb(): PagingSource<Int, Book> {
        return booksDao.getBooksFromDb()
    }

    override fun getBooksFromDbFlow(): Flow<List<Book>> {
        return booksDao.getBooksFromDbFlow()
    }

    //Paging3 Library
    @OptIn(ExperimentalPagingApi::class)
    override fun searchBooksFlow(query: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { BooksPagingSource(booksApiService, query)}
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getBooksCachedFlow(query: String): Flow<PagingData<Book>> {
        val pagingSourceFactory = { database.booksDao().getBooksFromDb()}
        var newQuery : String
        if(query.isEmpty()) newQuery = Constants.DEFAULT_QUERY else newQuery = query
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            remoteMediator = BooksRemoteMediator(
                apiService = booksApiService,
                database = database,
                query = newQuery,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    //Custom pagination
    override suspend fun getBooks(query: String, startIndex: String): List<Book> {
        return booksApiService.getBooksByStartIndex(query = query, startIndex = startIndex)
            .toBooksResponse().books
    }

    override suspend fun insertBooks(booksList: List<Book>) {
        return booksDao.insertBooks(booksList)
    }

    override suspend fun deleteBooks() {
        return booksDao.deleteBooks()
    }

    //Detail screen
    override suspend fun getBookByVolumeId(volumeId: String): Book {
        return booksApiService.getBookByVolumeId(volumeId).toBook()
    }
}
