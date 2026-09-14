package com.example.storewheel.data.repositories

import com.example.storewheel.domain.models.ProductDetailsModel
import com.example.storewheel.domain.models.ProductModel
import com.example.storewheel.network.api.ProductsApi
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsApi: ProductsApi) {
    suspend fun getProductsList(): Result<List<ProductModel>> {
        return try {
            val response = productsApi.getProducts()
            val domainList = response.products.map {
                ProductModel(
                    id = it.id ?: 0,
                    title = it.title ?: "",
                    imageUrl = it.thumbnail ?: "",
                    price = it.price ?: 0.0,
                )
            }
            return Result.success(domainList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFilteredProductsList(query: String): Result<List<ProductModel>> {
        return try {
            val response = productsApi.searchProducts(query)
            val domainList = response.products.map {
                ProductModel(
                    id = it.id ?: 0,
                    title = it.title ?: "",
                    imageUrl = it.thumbnail ?: "",
                    price = it.price ?: 0.0,
                )
            }
            return Result.success(domainList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProductById(id: Int): Result<ProductDetailsModel> {
        return try {
            val response = productsApi.getProductById(id)
            val productDetailsModel = ProductDetailsModel(
                id = response.id ?: 0,
                title = response.title ?: "",
                imageUrl = response.images ?: emptyList(),
                price = response.price ?: 0.0,
                rating = response.rating ?: 0f,
                description = response.description ?: "",
            )
            return Result.success(productDetailsModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}