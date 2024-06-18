package com.example.lightweight.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.Eating
import com.example.lightweight.Models.GetEating
import com.example.lightweight.R
import java.text.SimpleDateFormat


class EatingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val time :TextView = itemView.findViewById(R.id.foodtime)
    private val calories:TextView =itemView.findViewById(R.id.foodcalories)
    private val proteins:TextView = itemView.findViewById(R.id.foodproteins)
    private val fats:TextView = itemView.findViewById(R.id.foodfats)
    private val curbs:TextView = itemView.findViewById(R.id.foodcurbs)

    fun bind(item: GetEating){

        val timeString = item.dateTime.substring(11, 16)
        time.text = timeString
        var cal:Double = 0.0
        var fat:Double= 0.0
        var carbs:Double= 0.0
        var prot:Double= 0.0

        item.products.forEach {
            cal +=it.product.calories*it.quantity/100.0
            fat +=it.product.fats*it.quantity/100.0
            carbs+=it.product.carbohydrates*it.quantity/100.0
            prot += it.product.protein*it.quantity/100.0
        }
        calories.text =cal.toString() +" ккал."
        proteins.text = prot.toString()
        fats.text = fat.toString()
        curbs.text = carbs.toString()
    }

}