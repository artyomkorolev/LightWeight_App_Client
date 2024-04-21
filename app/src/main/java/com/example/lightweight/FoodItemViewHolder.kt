package com.example.lightweight

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val name: TextView =itemView.findViewById(R.id.nameItem)
    private val calories:TextView = itemView.findViewById(R.id.fooditemcal)
    private val proteins:TextView = itemView.findViewById(R.id.fooditemproteins)
    private val fats:TextView = itemView.findViewById(R.id.fooditemfats)
    private val carbs:TextView = itemView.findViewById(R.id.fooditemcarbs)

    fun bind(item: FoodItem){
        name.text = item.name
        calories.text = item.calories
        proteins.text = item.proteins
        fats.text = item.fats
        carbs.text = item.Carbs
    }
}