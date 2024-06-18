package com.example.lightweight.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.Models.Photo
import com.example.lightweight.ViewHolders.PhotoViewHolder
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.FoodItemViewHolder

class PhotoAdapter(private val photos:List<Photo>,
                   private val onItemClickListener: PhotoAdapter.OnItemClickListener):RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item,parent,false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
      return  photos.size

    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = photos[position]
        holder.bind(
            photoItem,
            {onItemClickListener.onDeleteClick(photoItem)


            }

        )

    }

    interface OnItemClickListener {
        fun onDeleteClick(photo: Photo)
    }
}