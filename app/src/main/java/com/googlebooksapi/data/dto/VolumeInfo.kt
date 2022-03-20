package com.googlebooksapi.data.dto

data class VolumeInfo(
    val title: String = "",
    val authors: List<String> = listOf(),
    val publisher: String = "",
    val publishedDate: String = "",
    val description: String = "",
    val industryIdentifiers: List<IndustryIdentifier> = listOf(),
    val readingModes: ReadingModes = ReadingModes(),
    val pageCount: Int = 0,
    val printType: String = "",
    val categories: List<String> = listOf(),
    val averageRating: Double = 0.0,
    val ratingsCount: Int = 0,
    val maturityRating: String = "",
    val allowAnonLogging: Boolean = false,
    val contentVersion: String = "",
    val panelizationSummary: PanelizationSummary = PanelizationSummary(),
    val imageLinks: ImageLinks = ImageLinks(),
    val language: String = "",
    val previewLink: String = "",
    val infoLink: String = "",
    val canonicalVolumeLink: String = "",
    val subtitle: String = ""
)