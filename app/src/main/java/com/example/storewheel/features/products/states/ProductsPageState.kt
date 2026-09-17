package com.example.storewheel.features.products.states

import com.example.storewheel.domain.models.ProductModel

sealed interface ProductsPageState {
    data object Loading : ProductsPageState
    data object PaginationLoad : ProductsPageState
    data object Empty : ProductsPageState
    data class Success(
        val products: List<ProductModel>,
        val isFiltered: Boolean = false,
        val isRefresh: Boolean = false,
        val isRefreshOnSwipe: Boolean = false
    ) : ProductsPageState

    data class Error(val message: String, val specificMessage: String) : ProductsPageState
}