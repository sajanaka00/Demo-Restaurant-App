package com.example.demorestaurantapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImagesAdapter(val context: Context, private val photos: List<String>)
    : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_menu_images, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photos = photos[position]
        holder.bind(photos)
    }

    override fun getItemCount() = photos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: String) {
            val menuImageUrl = itemView.findViewById<ImageView>(R.id.menuImage)

            Glide.with(context).load(photo).into(menuImageUrl)
        }
    }
}