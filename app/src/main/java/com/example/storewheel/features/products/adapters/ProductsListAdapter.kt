package com.example.storewheel.features.products.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storewheel.R
import com.example.storewheel.databinding.ItemProductListBinding
import com.example.storewheel.domain.models.ProductModel
import com.example.storewheel.features.products.adapters.utils.ProductDiffCallback

class ProductsListAdapter(private val navigationCallback: (Int) -> Unit) :
    ListAdapter<ProductModel,ProductsListAdapter.ViewHolder>(ProductDiffCallback()) {

    class ViewHolder(val view: ItemProductListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.productTitle.text = getItem(position).title
        holder.view.productPrice.text = getItem(position).price.toString()
        Glide.with(holder.itemView.context)
            .load(getItem(position).imageUrl)
            .placeholder(R.drawable.baseline_image_24)
            .error(R.drawable.baseline_error_outline_24)
            .into(holder.view.image)
        holder.itemView.setOnClickListener { navigationCallback(getItem(position).id) }
    }
}