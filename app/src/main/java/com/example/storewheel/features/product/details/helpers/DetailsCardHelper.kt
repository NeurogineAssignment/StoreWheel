package com.example.storewheel.features.product.details.helpers

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.storewheel.databinding.FragmentProductDetailsBinding
import com.example.storewheel.features.product.details.adapters.ProductImagesCarouselAdapter
import com.example.storewheel.features.product.details.states.ProductDetailsState

class DetailsCardHelper {

    fun stateHandler (
        binding: FragmentProductDetailsBinding,
        productDetailsState: ProductDetailsState,
        productImagesCarouselAdapter: ProductImagesCarouselAdapter,
        errorCallback: () -> Unit
        ) {
        when(productDetailsState) {
            is ProductDetailsState.Success -> updateProductDetails(binding,productDetailsState,productImagesCarouselAdapter)
            is ProductDetailsState.Loading -> renderLoadingState(binding)
            else -> {errorCallback()}
        }
    }

    private fun updateProductDetails(
        binding: FragmentProductDetailsBinding,
        productDetailsState: ProductDetailsState.Success,
        productImagesCarouselAdapter: ProductImagesCarouselAdapter
    ) {
        fillProductListContainer(binding,productDetailsState,productImagesCarouselAdapter)
        renderProductList(binding)
    }

    private fun fillProductListContainer(
        binding: FragmentProductDetailsBinding,
        productDetailsState: ProductDetailsState.Success,
        productImagesCarouselAdapter: ProductImagesCarouselAdapter
    ) {
        // update images carousel
        val product = productDetailsState.product
        productImagesCarouselAdapter.setImages(product.imageUrl)
        binding.imagesCarousel.adapter = productImagesCarouselAdapter
        // update fields
        binding.title.text = product.title
        binding.price.text = product.price.toString()
        binding.description.text = product.description
        binding.rating.rating = product.rating
    }

    fun renderProductList (binding: FragmentProductDetailsBinding) {
        binding.imagesCarousel.isVisible = true
        binding.stateLoading.root.isGone = true
    }

    fun renderLoadingState (binding: FragmentProductDetailsBinding) {
        binding.stateLoading.root.isVisible = true
        binding.imagesCarousel.isGone = true
    }

}