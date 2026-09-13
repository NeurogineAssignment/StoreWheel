package com.example.storewheel.features.product.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storewheel.R
import com.example.storewheel.databinding.ItemProductImageBinding

class ProductImagesCarouselAdapter : RecyclerView.Adapter<ProductImagesCarouselAdapter.ViewHolder>() {
    private var imageUrls: List<String> = emptyList()
    fun setImages(imageUrls: List<String>){
        this.imageUrls = imageUrls
        notifyItemRangeChanged(0,imageUrls.size)
    }
    class ViewHolder(val view: ItemProductImageBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemProductImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(imageUrls[position])
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.baseline_error_outline_24)
            .into(holder.view.image)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
}