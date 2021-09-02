package com.example.demorestaurantapp.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demorestaurantapp.R
import com.example.demorestaurantapp.data.model.ParentModel

class ParentAdapter(context: Context, items: ArrayList<ParentModel>) :
    RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    private val items: ArrayList<ParentModel> = items
    private val context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, null)

        return ParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (items != null) {
            return items.count()
        }

        return 0
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val name = items[position].title
        val sections = items[position].items

        val adapter = ChildAdapter(context, sections)

        holder.recyclerView.setHasFixedSize(true)
        holder.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerView.adapter = adapter

        holder.title.text = name
    }

    inner class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title) as TextView
        var recyclerView: RecyclerView = view.findViewById(R.id.childRecyclerView) as RecyclerView
    }

}