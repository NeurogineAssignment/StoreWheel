package com.example.storewheel.features.product.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.storewheel.commons.showErrorDialog
import com.example.storewheel.databinding.FragmentProductDetailsBinding
import com.example.storewheel.features.product.details.adapters.ProductImagesCarouselAdapter
import com.example.storewheel.features.product.details.helpers.DetailsCardHelper
import com.google.android.material.carousel.CarouselSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

/**
 * Shows the selected product's details
 */
@AndroidEntryPoint
class ProductDetails : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductDetailsViewmodel by viewModels()
    private val detailsCardHelper = DetailsCardHelper()
    private val productImagesCarouselAdapter = ProductImagesCarouselAdapter()

    // region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCarousel()
        setupBackNavigation()
        // initial operation , getProductDetails Via the selected product id
        val productId = arguments?.getInt("productId") ?: -1
        viewModel.getProductDetails(productId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {observeProductDetails()}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    // region setup
    private fun setupCarousel() {
        // to prevent partial images from showing
        CarouselSnapHelper().attachToRecyclerView(binding.imagesCarousel)
    }

    private fun setupBackNavigation() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
    //endregion
    private suspend fun observeProductDetails() {
       viewModel.productDetailsState.collect { productDetails ->
           detailsCardHelper.stateHandler(binding,productDetails,productImagesCarouselAdapter) {
               showErrorDialog(it.message,it.specificMessage).setOnDismissListener {
                   findNavController().navigateUp()
               }
           }
       }
    }
}