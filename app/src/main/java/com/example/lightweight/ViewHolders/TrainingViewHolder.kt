package com.example.lightweight.ViewHolders

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.GetTraining
import com.example.lightweight.Models.Training
import com.example.lightweight.R
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class TrainingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val time:TextView = itemView.findViewById(R.id.physicaltime)
    private val duration: TextView = itemView.findViewById(R.id.physicalDuration)

    fun bind(item: GetTraining){


        try {
//            val startTime = LocalDateTime.parse(item.startTime)
//            val endTime = LocalDateTime.parse(item.endTime)
//            Log.d("Время",startTime.toString() + " " + endTime.toString())

            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val startTime = LocalDateTime.parse(item.startTime, formatter)
            val endTime = LocalDateTime.parse(item.endTime,formatter)
            val durationS = Duration.between(startTime, endTime)
            val minutes = durationS.toMinutes().toString()
            time.text=item.startTime.substring(11, 16)

            duration.text=minutes
        } catch (e: DateTimeParseException) {
            time.text = item.startTime.substring(11, 16)
            duration.text =item.endTime.substring(11, 16)
        }

    }
}