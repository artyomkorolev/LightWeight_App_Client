package com.example.lightweight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrainingAdapter(private val trainigns: List<Training>, private val trainingActionListener:TrainingActionListener):RecyclerView.Adapter<TrainingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.phisical_item,parent,false)
        return TrainingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trainigns.size
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(trainigns[position])
        holder.itemView.setOnClickListener{
            trainingActionListener.OnClickItem(trainigns[position])
        }
    }
    interface TrainingActionListener{
        fun OnClickItem(training: Training)
    }
}