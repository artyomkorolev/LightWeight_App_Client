package com.example.lightweight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ExercizeAdapter(private val exercizes:List<Exercize>, private val exercizeActionListener:ExercizeActionListener):RecyclerView.Adapter<ExercizeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercizeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.physical_position_item,parent,false)
        return ExercizeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exercizes.size
    }

    override fun onBindViewHolder(holder: ExercizeViewHolder, position: Int) {
        holder.bind(exercizes[position])
        holder.itemView.setOnClickListener{
            exercizeActionListener.OnClickItem(exercizes[position])
        }
    }
    interface ExercizeActionListener{
        fun OnClickItem(exercize: Exercize)
    }
}