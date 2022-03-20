package com.googlebooksapi.domain.model

data class BooksResponse(val totalItems: Int,
                         val books: List<Book>)
