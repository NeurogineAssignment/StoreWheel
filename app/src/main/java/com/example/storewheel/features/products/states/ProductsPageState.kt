package com.example.storewheel.features.products.states

import com.example.storewheel.domain.ProductModel

sealed interface ProductsPageState {
    data object Loading : ProductsPageState
    data object Empty : ProductsPageState
    data class Success(val products: List<ProductModel>) : ProductsPageState
    data class Error(val message: String, val specificMessage: String) : ProductsPageState
}