package com.googlebooksapi.data.dto

data class SaleInfo(
    val country: String = "",
    val saleability: String = "",
    val isEbook: Boolean = false,
    val listPrice: ListPrice = ListPrice(),
    val retailPrice: RetailPrice = RetailPrice(),
    val buyLink: String = "",
    val offers: List<Offer> = listOf()
)