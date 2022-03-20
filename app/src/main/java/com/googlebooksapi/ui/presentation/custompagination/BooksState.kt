package com.googlebooksapi.ui.presentation.custompagination

import com.googlebooksapi.domain.model.Book

data class BooksState(
    val books : List<Book> = emptyList(),
    val startIndex: Int = 0,
    val currentScrollIndex : Int = 0,
    val isLoading: Boolean = false,
    val isLastPage : Boolean = false,
    val error: String = "",
    val query: String = ""
)
