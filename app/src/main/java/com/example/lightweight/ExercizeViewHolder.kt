package com.example.lightweight

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ExercizeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private val name : TextView = itemView.findViewById(R.id.exercizeName)
    private val measure : TextView = itemView.findViewById(R.id.exercizeMeasure)

    fun bind(item:Exercize){
        name.text = item.name
        measure.text = item.measure
    }
}