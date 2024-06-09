package com.app.ecosort.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.ecosort.R
import com.app.ecosort.view.description.TrashItem
import com.bumptech.glide.Glide

class TrashAdapter(private val items: List<TrashItem>) : RecyclerView.Adapter<TrashAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_trash, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imgFruit)
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitleFruit)

        fun bind(item: TrashItem) {
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(imageView)
            titleTextView.text = item.title
        }
    }
}