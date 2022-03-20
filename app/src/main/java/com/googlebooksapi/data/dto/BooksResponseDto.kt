package com.googlebooksapi.data.dto

import com.google.gson.annotations.SerializedName
import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.domain.model.BooksResponse

data class BooksResponseDto(
    val kind: String?,
    val totalItems: Int?,

    @SerializedName("items")
    val books: List<BookDto>?
)

fun BooksResponseDto.toBooksResponse() : BooksResponse
{   var list = mutableListOf<Book>()
    books?.map {
       list.add( it.toBook())
    }
    return BooksResponse(totalItems = totalItems ?: 0, books = list ?: emptyList())
}