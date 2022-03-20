package com.googlebooksapi.ui.presentation.common

sealed class BooksListEvent{
    class UpdateSearchQueryEvent(val query: String) : BooksListEvent()
    class GetNewBooksEvent : BooksListEvent()
    class GetMoreBooksEvent : BooksListEvent()
}
