package com.example.lightweight

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
    private val weight:TextView = itemView.findViewById(R.id.imageWeight)
    private val date: TextView = itemView.findViewById(R.id.imageDate)
    private val url: ImageView = itemView.findViewById(R.id.image)

    fun bind(item:Photo){
        weight.text=item.weight
        date.text = item.date
        Glide.with(itemView)
            .load(item.url)
            .centerCrop()
            .into(url)

    }
}