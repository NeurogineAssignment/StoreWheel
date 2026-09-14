package com.example.storewheel.domain.usecases

import com.example.storewheel.data.repositories.ProductsRepository
import com.example.storewheel.domain.models.ProductDetailsModel
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(id: Int): Result<ProductDetailsModel> {
        return productsRepository.getProductById(id)
    }
}