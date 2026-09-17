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
import kotlinx.coroutines.isActive
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

    private val _filteredProductsListState =
        MutableStateFlow<ProductsPageState>(ProductsPageState.Loading)
    val filteredProductsListState = _filteredProductsListState as StateFlow<ProductsPageState>

    private var searchJob: Job? = null
    private var currentSKip: Int = 0

    fun getProducts(isRefresh: Boolean = false,isRefreshOnSwipe: Boolean = false) {
        sendInitialLoadingState(isRefresh||isRefreshOnSwipe)
        viewModelScope.launch {
            val result = getProductsUseCase.invoke(currentSKip)
            result.fold(
                onSuccess = {
                    _productsListState.value = when {
                        it.isEmpty() && currentSKip == 0 -> ProductsPageState.Empty
                        isRefresh -> ProductsPageState.Success(it, isRefresh = true)
                        isRefreshOnSwipe -> ProductsPageState.Success(it, isRefreshOnSwipe = true)
                        else -> ProductsPageState.Success(it)
                    }
                },
                onFailure = {
                    val exception = it.message ?: "unknown error"
                    _productsListState.value = ProductsPageState.Error("Server Error", exception)
                }
            )
        }
    }

    private fun sendInitialLoadingState(isRefresh: Boolean) {
        // if refresh then reset skip
        if (isRefresh) currentSKip = 0 ; _productsListState.value = ProductsPageState.PaginationLoad
        // start indeterminant loading state
        if (currentSKip > 0) _productsListState.value = ProductsPageState.PaginationLoad
    }

    fun incrementSkip() {
        currentSKip += 20
    }

    fun searchProducts(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            // debounce effect
            delay(400L.milliseconds)
            val result = getFilteredProductsUseCase.invoke(query)
            // to prevent coroutine cancellation exception
            if (!isActive) return@launch
            result.fold(
                onSuccess = {
                    when {
                        it.isEmpty() -> _filteredProductsListState.value = ProductsPageState.Empty
                        else -> _filteredProductsListState.value =
                            ProductsPageState.Success(it, true)
                    }
                },
                onFailure = {
                    val exception = it.message ?: "unknown error"
                    _filteredProductsListState.value =
                        ProductsPageState.Error("Server Error", exception)
                }
            )
        }
    }
}