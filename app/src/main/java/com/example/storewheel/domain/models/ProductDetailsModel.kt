package com.example.storewheel.domain.models

class ProductDetailsModel (
    val id: Int,
    val title: String,
    val imageUrl: List<String>,
    val price: Double,
    val rating: Float,
    val description: String,
) {
    val isEmpty get() = id == 0
}

