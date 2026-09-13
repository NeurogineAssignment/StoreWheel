package com.example.storewheel.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storewheel.domain.usecases.GetFilteredProductsUseCase
import com.example.storewheel.domain.usecases.GetProductsUseCase
import com.example.storewheel.features.products.states.ProductsPageState
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
class ProductsPageViewmodel @Inject constructor(
    val getProductsUseCase: GetProductsUseCase,
    val getFilteredProductsUseCase: GetFilteredProductsUseCase
) : ViewModel() {

    private val _productsListState = MutableStateFlow<ProductsPageState>(ProductsPageState.Loading)
    val productsListState = _productsListState as StateFlow<ProductsPageState>

    private val _filteredProductsListState = MutableStateFlow<ProductsPageState>(ProductsPageState.Loading)
    val filteredProductsListState =  _filteredProductsListState as StateFlow<ProductsPageState>

    private var searchJob : Job? = null

    fun getProducts() {
        viewModelScope.launch {
            val result = getProductsUseCase.invoke()
            result.fold(
                onSuccess = {
                    when {
                       it.isEmpty() -> _productsListState.value = ProductsPageState.Empty
                        else -> _productsListState.value = ProductsPageState.Success(it)
                    }
                },
                onFailure = {
                    val exception = it.message ?: "unknown error"
                        _productsListState.value = ProductsPageState.Error("Server Error",exception)
                }
            )
        }
    }

    fun searchProducts(query: String){
        searchJob?.cancel()
        searchJob =  viewModelScope.launch {
            // debounce effect
            delay(400L.milliseconds)
            val result = getFilteredProductsUseCase.invoke(query)
            result.fold(
                onSuccess = {
                    when {
                        it.isEmpty() -> _filteredProductsListState.value = ProductsPageState.Empty
                        else -> _filteredProductsListState.value = ProductsPageState.Success(it)
                    }
                },
                onFailure = {
                    val exception = it.message ?: "unknown error"
                    _filteredProductsListState.value = ProductsPageState.Error("Server Error",exception)
                }
            )
        }
    }
}