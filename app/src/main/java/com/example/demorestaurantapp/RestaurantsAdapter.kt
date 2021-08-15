package com.example.demorestaurantapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demorestaurantapp.databinding.RecyclerRestaurantsBinding

// create adapter for the recyclerview to set all the items into recyclerview

class RestaurantsAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var restaurants = mutableListOf<YelpRestaurants>()

    fun setRestaurantList(restaurants: List<YelpRestaurants>) {
        this.restaurants = restaurants.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRestaurantsBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val restaurants = restaurants[position]
        holder.binding.name.text = restaurants.name
        holder.binding.ratings.text = "${restaurants.rating} Stars"
        holder.binding.review.text = "${restaurants.numReviews} Reviews"
        Glide.with(holder.itemView.context).load(restaurants.imageUrl).into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

}

class MainViewHolder(val binding: RecyclerRestaurantsBinding)
    : RecyclerView.ViewHolder(binding.root) {}