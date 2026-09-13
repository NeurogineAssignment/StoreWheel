package com.example.storewheel.features.product.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storewheel.domain.usecases.GetProductDetailsUseCase
import com.example.storewheel.features.product.details.states.ProductDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProductDetailsViewmodel @Inject constructor(
    val getProductDetailsUseCase: GetProductDetailsUseCase,
) : ViewModel() {

    private val _productDetailsState = MutableStateFlow<ProductDetailsState>(ProductDetailsState.Loading)
    val productDetailsState = _productDetailsState as StateFlow<ProductDetailsState>

    fun getProductDetails(id:Int) {
        viewModelScope.launch {
            val result = getProductDetailsUseCase.invoke(id)
            result.fold(
                onSuccess = {
                    _productDetailsState.value = ProductDetailsState.Success(it)
                },
                onFailure = {
                    val exception = it.message ?: "unknown error"
                    _productDetailsState.value = ProductDetailsState.Error("Server Error",exception)
                }
            )
        }
    }

}