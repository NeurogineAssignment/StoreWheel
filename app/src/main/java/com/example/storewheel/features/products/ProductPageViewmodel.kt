package com.example.storewheel.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storewheel.domain.usecases.GetFilteredProductsUsecase
import com.example.storewheel.domain.usecases.GetProductsUsecase
import com.example.storewheel.features.products.states.ProductPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/*
* Communication between the Presentation and Data layers
* Sends requests to data layer , then Maps the server results to UI states
* Handles any presentation layer's business logic
*/
@HiltViewModel
class ProductPageViewmodel @Inject constructor(
    val getProductsUsecase: GetProductsUsecase,
    val getFilteredProductsUsecase: GetFilteredProductsUsecase
) : ViewModel() {

    private val _productsListState = MutableStateFlow<ProductPageState>(ProductPageState.Loading)
    val productsListState = _productsListState as StateFlow<ProductPageState>

    private val _filteredProductsListState = MutableStateFlow<ProductPageState>(ProductPageState.Loading)
    val filteredProductsListState =  _filteredProductsListState as StateFlow<ProductPageState>

    private var searchJob : Job? = null

    fun getProducts() {
        viewModelScope.launch {
            val result = getProductsUsecase.invoke()
            result.fold(
                onSuccess = {
                    when {
                       it.isEmpty() -> _productsListState.value = ProductPageState.Empty
                        else -> _productsListState.value = ProductPageState.Success(it)
                    }
                },
                onFailure = {
                    val exception = it.message ?: "unknown error"
                        _productsListState.value = ProductPageState.Error("Server Error",exception)
                }
            )
        }
    }

    fun searchProducts(query: String){
        searchJob?.cancel()
        searchJob =  viewModelScope.launch {
            // debounce effect
            delay(400L.milliseconds)
            val result = getFilteredProductsUsecase.invoke(query)
            result.fold(
                onSuccess = {
                    when {
                        it.isEmpty() -> _filteredProductsListState.value = ProductPageState.Empty
                        else -> _filteredProductsListState.value = ProductPageState.Success(it)
                    }
                },
                onFailure = {
                    val exception = it.message ?: "unknown error"
                    _filteredProductsListState.value = ProductPageState.Error("Server Error",exception)
                }
            )
        }
    }
}