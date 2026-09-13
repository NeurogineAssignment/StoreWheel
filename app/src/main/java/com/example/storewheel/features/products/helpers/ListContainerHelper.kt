package com.example.storewheel.features.products.helpers

import android.content.Context
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.storewheel.R
import com.example.storewheel.databinding.ViewProductsListBinding
import com.example.storewheel.features.products.adapters.ProductsListAdapter
import com.example.storewheel.features.products.states.ProductPageState

class ListContainerHelper {

    fun stateHandler (
        context: Context,
        binding: ViewProductsListBinding,
        productPageState: ProductPageState,
        errorCallback: () -> Unit
        ) {
        when(productPageState) {
            is ProductPageState.Success -> updateProductListContainer(context,binding,productPageState)
            is ProductPageState.Empty -> renderEmptyState(binding)
            is ProductPageState.Loading -> renderLoadingState(binding)
            else -> {errorCallback()}
        }
    }

    private fun updateProductListContainer(
        context: Context,
        binding: ViewProductsListBinding,
        productPageState: ProductPageState.Success
    ) {
        fillProductListContainer(context,binding,productPageState)
        renderProductList(binding)
    }

    private fun fillProductListContainer(
        context: Context,
        binding: ViewProductsListBinding,
        productPageState: ProductPageState.Success
    ) {
        val productsListAdapter = ProductsListAdapter()
        val productsList = productPageState.products
        productsListAdapter.setProducts(productPageState.products)
        binding.productsList.adapter = productsListAdapter
        // update label
        binding.productsListLabel.text = context.getString(R.string.items_available,productsList.size)
    }

    fun renderProductList (binding: ViewProductsListBinding) {
        binding.recyclerViewContainer.isVisible = true
        binding.stateLoading.root.isGone = true
        binding.stateEmpty.root.isGone = true
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
    }

}