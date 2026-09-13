package com.example.storewheel.features.product.details.states

import com.example.storewheel.domain.ProductDetailsModel

sealed interface ProductDetailsState {
    data object Loading : ProductDetailsState
    data class Success(val product: ProductDetailsModel) : ProductDetailsState
    data class Error(val message: String, val specificMessage: String) : ProductDetailsState
}