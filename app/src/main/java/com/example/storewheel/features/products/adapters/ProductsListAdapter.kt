package com.example.storewheel.features.products.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storewheel.R
import com.example.storewheel.databinding.ItemProductListBinding
import com.example.storewheel.domain.ProductModel

class ProductsListAdapter(private val navigationCallback : (Int) -> Unit) : RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {
    private var products: List<ProductModel> = emptyList()
    fun setProducts(products: List<ProductModel>){
        this.products = products
        notifyItemRangeChanged(0,products.size)
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
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.baseline_error_outline_24)
            .into(holder.view.image)
        holder.itemView.setOnClickListener { navigationCallback(products[position].id) }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}