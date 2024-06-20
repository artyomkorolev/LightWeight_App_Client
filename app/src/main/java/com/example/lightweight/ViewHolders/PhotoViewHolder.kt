package com.example.lightweight.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.Models.Photo
import com.example.lightweight.R

class PhotoViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
    private val weight:TextView = itemView.findViewById(R.id.imageWeight)
    private val date: TextView = itemView.findViewById(R.id.imageDate)
    private val url: ImageView = itemView.findViewById(R.id.image)
    private val delete: ImageView = itemView.findViewById(R.id.buttonDelete)

    fun bind(item: Photo,
             onDeleteClick: (Photo) -> Unit){
        weight.text= item.weight.toString()
        date.text = item.dateTime
        url.setImageBitmap(item.image)
        delete.setOnClickListener{
            onDeleteClick(item)
        }

    }
}