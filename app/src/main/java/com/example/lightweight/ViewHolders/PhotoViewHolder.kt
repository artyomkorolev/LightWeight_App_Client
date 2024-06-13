package com.example.lightweight.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lightweight.Models.Photo
import com.example.lightweight.R

class PhotoViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
    private val weight:TextView = itemView.findViewById(R.id.imageWeight)
    private val date: TextView = itemView.findViewById(R.id.imageDate)
    private val url: ImageView = itemView.findViewById(R.id.image)

    fun bind(item: Photo){
        weight.text= item.weight.toString()
        date.text = item.dateTime
        Glide.with(itemView)
            .load(item.image)
            .centerCrop()
            .into(url)

    }
}