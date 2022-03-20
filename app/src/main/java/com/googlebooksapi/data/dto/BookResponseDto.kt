package com.googlebooksapi.data.dto

import androidx.room.PrimaryKey
import com.googlebooksapi.domain.model.Book

data class BookResponseDto(
    val kind: String?,
    val id: String?,
    val etag: String?,
    val selfLink: String?,
    val volumeInfo: VolumeInfo?,
    val saleInfo: SaleInfo?,
    val accessInfo: AccessInfo?
)

fun BookResponseDto.toBook(): Book {
    return Book(
        kind ?: "",
        id ?: "0",
        volumeInfo?.title ?: "",
        volumeInfo?.authors ?: listOf(),
        volumeInfo?.publisher ?: "",
        volumeInfo?.publishedDate ?: "",
        volumeInfo?.description ?: "",
        volumeInfo?.averageRating ?: 0.0,
        volumeInfo?.ratingsCount ?: 0,
        volumeInfo?.imageLinks?.thumbnail ?: ""
    )
}
