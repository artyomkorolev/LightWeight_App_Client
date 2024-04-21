package com.example.lightweight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FoodItemAdapter(private val fooditems: List<FoodItem>,private val foodItemActionListener: FoodItemActionListener):RecyclerView.Adapter<FoodItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_position_item,parent,false)
        return FoodItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fooditems.size
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
       holder.bind(fooditems[position])
        holder.itemView.setOnClickListener{
            foodItemActionListener.OnClickItem(fooditems[position])
        }
    }

    interface FoodItemActionListener{
        fun OnClickItem(foodItem: FoodItem)
    }


}