package com.example.demorestaurantapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RestaurantsAdapter(val context: Context,
                         private val restaurants: List<YelpRestaurants>,
                         private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_restaurants, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurants = restaurants[position]
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
        holder.bind(restaurants)
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpRestaurants) {
            val imageUrl = itemView.findViewById<ImageView>(R.id.image)
            val name = itemView.findViewById<TextView>(R.id.name)
            val rating = itemView.findViewById<TextView>(R.id.ratings)
            val reviewCount = itemView.findViewById<TextView>(R.id.review)

            name.text = restaurant.name
            rating.text = "${restaurant.rating} Stars"
            reviewCount.text = "${restaurant.numReviews} Reviews"
            Glide.with(context).load(restaurant.imageUrl).into(imageUrl)
        }
    }

}