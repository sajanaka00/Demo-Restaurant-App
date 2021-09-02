package com.example.demorestaurantapp.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demorestaurantapp.R
import com.example.demorestaurantapp.data.model.YelpRestaurants

class ChildAdapter(context: Context, items: ArrayList<YelpRestaurants>) :
    RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    private var items: ArrayList<YelpRestaurants> = items
    private val context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, null)

        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (items != null) {
            return items.count()
        }

        return 0
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val restaurants = items[position]
        holder.bind(restaurants)
    }

    inner class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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