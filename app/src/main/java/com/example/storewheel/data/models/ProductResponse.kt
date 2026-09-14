package com.example.storewheel.data.models


data class ProductsResponse (
    val products: List<ProductResponse>
)

data class ProductResponse(
    val id: Int?,
    val title: String?,
    val thumbnail: String?,
    val price: Double?,
)