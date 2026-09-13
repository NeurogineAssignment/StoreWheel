package com.example.storewheel.domain.usecases

import com.example.storewheel.domain.ProductModel
import javax.inject.Inject

class GetFilteredProductsUseCase @Inject constructor() {
    operator fun invoke (query: String) : Result<List<ProductModel>>{
        // Mock data for domain testing purpose
        val dummyProductList = listOf(
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