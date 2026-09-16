package com.example.storewheel.features.products.adapters.utils

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.storewheel.domain.models.ProductModel

class ProductDiffCallback : ItemCallback<ProductModel>(){
    override fun areItemsTheSame(
        oldItem: ProductModel,
        newItem: ProductModel
    ): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ProductModel,
        newItem: ProductModel
    ): Boolean {
        return oldItem== newItem
    }
}