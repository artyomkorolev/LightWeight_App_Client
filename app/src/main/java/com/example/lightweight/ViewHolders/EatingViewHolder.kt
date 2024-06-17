package com.example.lightweight.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.Eating
import com.example.lightweight.R


class EatingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val time :TextView = itemView.findViewById(R.id.foodtime)
    private val calories:TextView =itemView.findViewById(R.id.foodcalories)
    private val proteins:TextView = itemView.findViewById(R.id.foodproteins)
    private val fats:TextView = itemView.findViewById(R.id.foodfats)
    private val curbs:TextView = itemView.findViewById(R.id.foodcurbs)

    fun bind(item: Eating){
        time.text = item.time
        calories.text = item.calories
        proteins.text = item.proteins
        fats.text = item.fats
        curbs.text = item.curbs
    }

}