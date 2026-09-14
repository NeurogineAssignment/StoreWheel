package com.example.storewheel.domain.usecases

import com.example.storewheel.data.repositories.ProductsRepository
import com.example.storewheel.domain.models.ProductModel
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(skip: Int): Result<List<ProductModel>> {
        // Mock data for domain testing purpose
        return productsRepository.getProductsList(skip)
    }
}