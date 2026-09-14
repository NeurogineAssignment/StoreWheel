package com.example.storewheel.domain.usecases

import com.example.storewheel.data.repositories.ProductsRepository
import com.example.storewheel.domain.models.ProductModel
import javax.inject.Inject

class GetFilteredProductsUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(query: String): Result<List<ProductModel>> {
        // Mock data for domain testing purpose
        return productsRepository.getFilteredProductsList(query)
    }
}