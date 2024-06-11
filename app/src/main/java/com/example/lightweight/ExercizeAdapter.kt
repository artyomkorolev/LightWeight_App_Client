package com.example.lightweight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.E

class ExercizeAdapter(private val exercizes:List<Exercize>, private val exercizeActionListener:ExercizeActionListener,
                      private val onItemClickListener: OnItemClickListener,private val hideElements: Boolean = false
):RecyclerView.Adapter<ExercizeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercizeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.physical_position_item,parent,false)
        return ExercizeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exercizes.size
    }

    override fun onBindViewHolder(holder: ExercizeViewHolder, position: Int) {

        val exercizeItem = exercizes[position]
        holder.bind(
            exercizeItem,
            { onItemClickListener.onSaveClick(exercizeItem) },
            { onItemClickListener.onDeleteClick(exercizeItem) },
            { item, newCount -> onItemClickListener.onGrammChange(item, newCount) }, hideElements
        )
        holder.itemView.setOnClickListener{
            exercizeActionListener.OnClickItem(exercizes[position])
        }
    }
    interface ExercizeActionListener{
        fun OnClickItem(exercize: Exercize)
    }
    interface OnItemClickListener {
        fun onSaveClick(exercize: Exercize)
        fun onDeleteClick(exercize: Exercize)
        fun onGrammChange(exercize: Exercize, newCount: String)
    }
}