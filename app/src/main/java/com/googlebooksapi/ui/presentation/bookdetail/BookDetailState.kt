package com.googlebooksapi.ui.presentation.bookdetail

import com.googlebooksapi.domain.model.Book

data class BookDetailState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
