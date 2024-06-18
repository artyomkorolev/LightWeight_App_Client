package com.example.lightweight.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.ViewHolders.EatingViewHolder
import com.example.lightweight.Models.Eating
import com.example.lightweight.R

class EatingAdapter(private val eatings: List<Eating>, private  val eatingActionListener: EatingActionListener, private val totalCalTextView: TextView): RecyclerView.Adapter<EatingViewHolder>() {

    private var totalCalories:Int = 0

    private fun updateTotalCalories(){
//        totalCalories = eatings.sumBy { it.calories.toInt() }
    }

    fun updateCalories() {
        updateTotalCalories()
        totalCalTextView.text = totalCalories.toString()
        notifyDataSetChanged()
    }

    // Метод для получения суммы калорий
    fun getTotalCalories(): Int {
        return totalCalories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EatingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item,parent,false)
        return EatingViewHolder(view)
    }

    override fun getItemCount(): Int {
       return eatings.size
    }

    override fun onBindViewHolder(holder: EatingViewHolder, position: Int) {
        holder.bind(eatings[position])
        holder.itemView.setOnClickListener{
            eatingActionListener.OnClickItem(eatings[position])
        }
    }
    interface EatingActionListener{
        fun OnClickItem(eating: Eating)
    }
}