package com.googlebooksapi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.googlebooksapi.data.dto.AccessInfo
import com.googlebooksapi.data.dto.SaleInfo
import com.googlebooksapi.data.dto.VolumeInfo

@Entity(tableName = "book")
data class Book(
    val kind: String,
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String = "",
    val authors: List<String> = listOf(),
    val publisher: String = "",
    val publishedDate: String = "",
    val description: String = "",
    val averageRating: Double = 0.0,
    val ratingsCount: Int = 0,
    val thumbnail: String = ""
)
