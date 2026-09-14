package com.example.storewheel.domain.usecases

import com.example.storewheel.domain.models.ProductDetailsModel
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor() {
    operator fun invoke (id:Int) : Result<ProductDetailsModel>{
        // Mock data for domain testing purpose
        val product = ProductDetailsModel(
            id = 2,
            title = "Eyeshadow Palette with Mirror",
            imageUrl = listOf(
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
                "https://cdn.dummyjson.com/product-images/fragrances/dolce-shine-eau-de/1.webp"
            ),
            price = 19.99,
            rating = 3.7f,
            description = "Dolce Shine by Dolce & Gabbana is a vibrant and fruity fragrance, featuring notes of mango, jasmine, and blonde woods. It's a joyful and youthful scent"
        )
        return Result.success(product)
    }
}