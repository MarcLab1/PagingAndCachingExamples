package com.googlebooksapi.ui.presentation.custompagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.domain.usecase.GetBooksUseCase
import com.googlebooksapi.ui.presentation.common.BooksListEvent
import com.googlebooksapi.utils.Constants
import com.googlebooksapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CustomPaginationViewModel
@Inject constructor(
    private val booksUseCase: GetBooksUseCase
) : ViewModel() {

    var booksState by mutableStateOf(BooksState())
        private set

    fun OnEvent(event: BooksListEvent) {
        when (event) {
            is BooksListEvent.UpdateSearchQueryEvent -> { updateQuery(event.query) }
            is BooksListEvent.GetNewBooksEvent -> { getNewBooks() }
            is BooksListEvent.GetMoreBooksEvent -> { getMoreBooks() }
        }
    }

    private fun getNewBooks() {
        this.booksState = booksState.copy(
            books = emptyList(),
            startIndex = 0,
            currentScrollIndex = 0,
            isLoading = false,
            error = "",
            isLastPage = false
        )
        getBooks()
    }

    fun getMoreBooks() {
        if ((booksState.currentScrollIndex + 1) >= (booksState.startIndex - 1))
            getBooks()
    }

    fun getBooks() {
        booksUseCase(booksState.query, booksState.startIndex.toString()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    var newBooks = booksState.books
                    if (result.data is List<Book>)
                        newBooks += result.data
                    val nextIsNull = result.data?.size == 0
                    booksState = booksState.copy(
                        books = newBooks,
                        startIndex = booksState.startIndex + Constants.PAGE_SIZE,
                        isLoading = false,
                        error = "",
                        isLastPage = nextIsNull
                    )
                }
                is Resource.Error -> {
                    booksState = booksState.copy(
                        isLoading = false,
                        error = result.message ?: Constants.ERROR_MSG
                    )
                }
                is Resource.Loading -> {
                    booksState = booksState.copy(isLoading = true, error = "")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateQuery(newQuery: String) {
        booksState = booksState.copy(query = newQuery)
    }

    fun updateCurrentScrollIndex(newIndex: Int) {
        booksState = booksState.copy(currentScrollIndex = newIndex)
    }
}