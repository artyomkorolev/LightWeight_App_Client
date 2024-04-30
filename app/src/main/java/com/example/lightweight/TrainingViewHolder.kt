package com.example.lightweight

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrainingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val time:TextView = itemView.findViewById(R.id.physicaltime)
    private val duration: TextView = itemView.findViewById(R.id.physicalDuration)

    fun bind(item:Training){
        time.text=item.time
        duration.text=item.duration
    }
}