package com.example.storewheel.features.products.states

import com.example.storewheel.domain.ProductModel

sealed interface ProductPageState {
    data object Loading : ProductPageState
    data object Empty : ProductPageState
    data class Success(val products: List<ProductModel>) : ProductPageState
    data class Error(val message: String, val specificMessage: String) : ProductPageState
}