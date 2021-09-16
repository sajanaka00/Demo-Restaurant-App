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
import android.view.animation.AlphaAnimation

class ChildAdapter(context: Context, items: ArrayList<YelpRestaurants>) :
    RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    private var items: ArrayList<YelpRestaurants> = items
    private val context: Context = context
    private val FADE_DURATION = 2000 // FADE_DURATION in milliseconds

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

        // Set the view to fade in
        setFadeAnimation(holder.itemView);
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

    private fun setFadeAnimation(view: View) {
        // AlphaAnimation(float fromAlpha, float toAlpha)
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

}