package com.example.demorestaurantapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demorestaurantapp.data.model.YelpBusinessDetail
import com.example.demorestaurantapp.databinding.RecyclerMenuImagesBinding

class ResImagesAdapter : RecyclerView.Adapter<RIViewHolder>() {

    private var restaurantImages = mutableListOf<String>()

    fun setRestaurantList(restaurantImages: List<String>) {
        this.restaurantImages = restaurantImages.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RIViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerMenuImagesBinding.inflate(inflater, parent, false)
        return RIViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RIViewHolder, position: Int) {
        val photos = restaurantImages[position]
        Glide.with(holder.itemView.context).load(photos).into(holder.binding.menuImage)
    }

    override fun getItemCount(): Int {
        return restaurantImages.size
    }

}

class RIViewHolder(
    val binding: RecyclerMenuImagesBinding)
    : RecyclerView.ViewHolder(binding.root) {}