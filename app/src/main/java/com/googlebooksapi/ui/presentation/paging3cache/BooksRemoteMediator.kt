package com.googlebooksapi.ui.presentation.paging3cache

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.googlebooksapi.data.ApiService
import com.googlebooksapi.data.dto.toBooksResponse
import com.googlebooksapi.domain.database.Database
import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.domain.model.RemoteKeys
import com.googlebooksapi.utils.Constants
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class BooksRemoteMediator(
    private val apiService: ApiService,
    private val database: Database,
    private val query: String,
) : RemoteMediator<Int, Book>() {

    private val booksDao = database.booksDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        pagingState: PagingState<Int, Book>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(pagingState)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(pagingState)
                    val prevPage = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = (remoteKeys != null)
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(pagingState)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = (remoteKeys != null)
                        )
                    nextPage
                }
            }

            val books = apiService.getBooksByStartIndex(
                query = query,
                startIndex = ((currentPage - 1) * Constants.PAGE_SIZE).toString()
            ).toBooksResponse().books
            val endOfPaginationReached = books.isEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    booksDao.deleteBooks()
                    remoteKeysDao.deleteRemoteKeys()
                }
                val keys = books.map { key ->
                    key.id?.let {
                        RemoteKeys(
                            id = it,
                            prevKey = prevPage,
                            nextKey = nextPage
                        )
                    }
                }
                if (keys != null) {
                    remoteKeysDao.addRemoteKeys(remoteKeys = keys)
                }
                booksDao.insertBooks(books)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Book>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                remoteKeysDao.getRemoteKeys(id = it)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Book>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                remoteKeysDao.getRemoteKeys(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Book>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                remoteKeysDao.getRemoteKeys(id = it.id)
            }
    }
}
