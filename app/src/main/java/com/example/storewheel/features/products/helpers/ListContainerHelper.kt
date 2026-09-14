package com.example.storewheel.features.products.helpers

import android.content.Context
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.storewheel.R
import com.example.storewheel.databinding.ViewProductsListBinding
import com.example.storewheel.features.products.adapters.ProductsListAdapter
import com.example.storewheel.features.products.states.ProductsPageState

class ListContainerHelper {

    fun stateHandler (
        context: Context,
        binding: ViewProductsListBinding,
        productsPageState: ProductsPageState,
        productsListAdapter: ProductsListAdapter,
        errorCallback: (ProductsPageState.Error) -> Unit
        ) {
        when(productsPageState) {
            is ProductsPageState.Success -> updateProductListContainer(context,binding,productsListAdapter,productsPageState)
            is ProductsPageState.Empty -> renderEmptyState(binding)
            is ProductsPageState.Loading -> renderLoadingState(binding)
            is ProductsPageState.PaginationLoad -> renderIndeterminateLoad(binding)
            is ProductsPageState.Error -> errorCallback(productsPageState)
        }
    }
    private fun updateProductListContainer(
        context: Context,
        binding: ViewProductsListBinding,
        productsListAdapter: ProductsListAdapter,
        productsPageState: ProductsPageState.Success
    ) {
        fillProductListContainer(context,binding,productsPageState,productsListAdapter)
        renderProductList(binding)
    }

    private fun fillProductListContainer(
        context: Context,
        binding: ViewProductsListBinding,
        productsPageState: ProductsPageState.Success,
        productsListAdapter: ProductsListAdapter
    ) {
        val productsList = productsPageState.products
        // update list based on mode
        if(productsPageState.isFiltered || productsPageState.isRefresh) {
            productsListAdapter.refreshProducts(productsList)
        } else {
            productsListAdapter.updateProducts(productsList)
        }
        // update label
        binding.productsListLabel.text = context.getString(R.string.items_available,productsListAdapter.itemCount)
    }

    fun renderProductList (binding: ViewProductsListBinding) {
        binding.recyclerViewContainer.isVisible = true
        binding.stateLoading.root.isGone = true
        binding.stateEmpty.root.isGone = true
        binding.progressIndicator.isGone = true
    }

    fun renderLoadingState (binding: ViewProductsListBinding) {
        binding.stateLoading.root.isVisible = true
        binding.stateEmpty.root.isGone = true
        binding.recyclerViewContainer.isGone = true
    }

    fun renderEmptyState (binding: ViewProductsListBinding) {
        binding.stateEmpty.root.isVisible = true
        binding.stateLoading.root.isGone = true
        binding.recyclerViewContainer.isGone = true
        binding.progressIndicator.isGone = true
    }

    private fun renderIndeterminateLoad(binding: ViewProductsListBinding) {
        binding.progressIndicator.isVisible = true
    }

}