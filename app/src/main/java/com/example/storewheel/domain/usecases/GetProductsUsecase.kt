package com.example.storewheel.domain.usecases

import com.example.storewheel.domain.ProductModel
import javax.inject.Inject

class GetProductsUsecase @Inject constructor() {
    operator fun invoke () : Result<List<ProductModel>>{
        // Mock data for domain testing purpose
        val dummyProductList = listOf(
            ProductModel(
                id = 1,
                title = "Essence Mascara Lash Princess",
                imageUrl = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
                price = 9.99
            ),
            ProductModel(
                id = 2,
                title = "Eyeshadow Palette with Mirror",
                imageUrl = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
                price = 19.99
            ),
            ProductModel(
                id = 3,
                title = "Powder Canister",
                imageUrl = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
                price = 14.99
            ),
            ProductModel(
                id = 4,
                title = "Red Lipstick",
                imageUrl = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
                price = 12.99
            ),
            ProductModel(
                id = 5,
                title = "Red Nail Polish",
                imageUrl = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
                price = 8.99
            ),
            ProductModel(
                id = 6,
                title = "Calvin Klein CK One",
                imageUrl = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
                price = 49.00
            )
        )

        return Result.success(dummyProductList)
    }
}