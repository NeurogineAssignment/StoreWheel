package com.example.storewheel.data.models

data class ProductDetailsResponse(
    val id: Int?,
    val title: String?,
    val images: List<String>?,
    val price: Double?,
    val rating: Float?,
    val description: String?,
)