package com.example.storewheel.features.products.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storewheel.R
import com.example.storewheel.databinding.ItemProductListBinding
import com.example.storewheel.domain.models.ProductModel

class ProductsListAdapter(private val navigationCallback : (Int) -> Unit) : RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {
    private var products: List<ProductModel> = emptyList()
    private var currentProductSize = 0
    fun updateProducts(products: List<ProductModel>){
        if(this.products.isEmpty()) {
            this.products = products
            notifyItemRangeChanged(0,products.size)
            currentProductSize = products.size
        } else {
            this.products += products
            notifyItemRangeInserted(currentProductSize, products.size)
            currentProductSize = this.products.size
        }
    }

    fun refreshProducts(products: List<ProductModel>){
        this.products = products
        notifyDataSetChanged()
    }

    class ViewHolder(val view: ItemProductListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemProductListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.productTitle.text = products[position].title
        holder.view.productPrice.text = products[position].price.toString()
        Glide.with(holder.itemView.context)
            .load(products[position].imageUrl)
            .placeholder(R.drawable.baseline_image_24)
            .error(R.drawable.baseline_error_outline_24)
            .into(holder.view.image)
        holder.itemView.setOnClickListener { navigationCallback(products[position].id) }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}