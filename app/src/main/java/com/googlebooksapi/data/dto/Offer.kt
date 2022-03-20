package com.googlebooksapi.data.dto

data class Offer(
    val finskyOfferType: Int?,
    val listPrice: ListPrice?,
    val retailPrice: RetailPrice?,
    val giftable: Boolean?
)