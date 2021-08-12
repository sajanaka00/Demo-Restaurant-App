package com.example.demorestaurantapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demorestaurantapp.databinding.RecyclerRestaurantsBinding
import com.yelp.fusion.client.models.Business

class RestaurantsViewAdapter: RecyclerView.Adapter<RestaurantsViewAdapter.ItemViewHolder>() {

    var data = ArrayList<Business>()

    class ItemViewHolder(val binding: RecyclerRestaurantsBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(RecyclerRestaurantsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val business = data[position]

        val name = "${business.name}"
        val rating = "${business.rating} Stars"
        val reviewCount = "${business.reviewCount} Reviews"

        holder.binding.name.text = name
        holder.binding.ratings.text = rating
        holder.binding.review.text = reviewCount

        Glide.with(holder.itemView.context).load(business.imageUrl).into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}