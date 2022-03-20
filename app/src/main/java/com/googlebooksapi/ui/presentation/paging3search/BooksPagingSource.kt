package com.googlebooksapi.ui.presentation.paging3search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.googlebooksapi.data.ApiService
import com.googlebooksapi.data.dto.toBooksResponse
import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class BooksPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val currentPage = params.key ?: 0 //startIndex begins at zero
            val books = apiService.getBooksByStartIndex(
                query = if (query.isEmpty()) Constants.DEFAULT_QUERY else query,
                startIndex = (currentPage * Constants.PAGE_SIZE).toString()
            ).toBooksResponse().books

            LoadResult.Page(
                data = books,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (books == null || books.isEmpty()) null else currentPage + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }

}