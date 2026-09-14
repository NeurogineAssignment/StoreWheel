package com.example.storewheel.features.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.storewheel.R
import com.example.storewheel.commons.showErrorDialog
import com.example.storewheel.databinding.FragmentProductPageBinding
import com.example.storewheel.features.products.adapters.ProductsListAdapter
import com.example.storewheel.features.products.helpers.ListContainerHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Shows the list of products with basic info and allows the user to search through them.
 */
@AndroidEntryPoint
class ProductsPage : Fragment() {

    private var _binding: FragmentProductPageBinding? = null
    private val viewModel: ProductsPageViewmodel by viewModels()
    private val listContainerHelper = ListContainerHelper()
    private val productsListAdapter = ProductsListAdapter(navigationCallback = ::navigateToProductDetails)
    private val filteredProductsListAdapter = ProductsListAdapter(navigationCallback = ::navigateToProductDetails)


    // region lifecycle

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductPageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        // initial operation
        viewModel.getProducts()

        // observe only when device is not in background and witin the fragment's lifecycle,
        // it is also needed since we use kotlin flow and not livedata
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {observeProducts()}
                launch {observeFilteredProducts()}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //endregion

    // region setup
    private fun setupUI() {
        searchSetup()
        setupRefreshList()
    }

    private fun setupRefreshList() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.refresh -> {
                    viewModel.getProducts()
                    true
                }
                else -> false
            }
        }
    }

    private fun searchSetup() {
        binding.searchView.editText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchProducts(text.toString())
        }
    }
    //endregion

    // region observables

    private suspend fun observeProducts() {
        viewModel.productsListState.collect { productsPageState ->
            listContainerHelper.stateHandler(requireContext(),binding.productsListContainer,productsPageState,productsListAdapter) {
                showErrorDialog(it.message,it.specificMessage)
            }
        }
    }


    private suspend fun observeFilteredProducts() {
        viewModel.filteredProductsListState.collect { productsPageState ->
            listContainerHelper.stateHandler(requireContext(),binding.filteredProductsListContainer,productsPageState,filteredProductsListAdapter) {
                showErrorDialog(it.message,it.specificMessage)
            }
        }
    }

    //endregion

    // region navigation

    private fun navigateToProductDetails(productId:Int) {
        val bundle = bundleOf("productId" to productId)
        findNavController().navigate(
            R.id.action_ProductPageFragment_to_ProductDetailsFragment,
            bundle)
    }

    //endregion

}