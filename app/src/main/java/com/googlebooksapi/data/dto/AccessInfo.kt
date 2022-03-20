package com.googlebooksapi.data.dto

data class AccessInfo(
    val country: String = "",
    val viewability: String = "",
    val embeddable: Boolean = false,
    val publicDomain: Boolean = false,
    val textToSpeechPermission: String = "",
    val epub: Epub = Epub(),
    val pdf: Pdf = Pdf(),
    val webReaderLink: String = "",
    val accessViewStatus: String = "",
    val quoteSharingAllowed: Boolean = false
)