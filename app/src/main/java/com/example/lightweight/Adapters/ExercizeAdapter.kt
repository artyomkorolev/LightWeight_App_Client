package com.example.lightweight.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.Exercize
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.ExercizeViewHolder

class ExercizeAdapter(private var exercizes:List<Exercize>, private val exercizeActionListener: ExercizeActionListener,
                      private val onItemClickListener: OnItemClickListener, private val hideElements: Boolean = false
):RecyclerView.Adapter<ExercizeViewHolder>() {
    private var originalItems: List<Exercize> = exercizes
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
            { onItemClickListener.onSaveClick(exercizeItem)
                exercizeItem.isSaved = true
                sortItems()
                notifyDataSetChanged()
            },
            { onItemClickListener.onDeleteClick(exercizeItem)
                exercizeItem.isSaved = false
                sortItems()
                notifyDataSetChanged()
            },
            { item, newCount -> onItemClickListener.onGrammChange(item, newCount) }, hideElements
        )
        holder.itemView.setOnClickListener{
            exercizeActionListener.OnClickItem(exercizes[position])
        }
    }
    private fun sortItems() {
        exercizes = exercizes.sortedByDescending { it.isSaved }
    }
    fun filterItems(text: String) {
        if (text.isEmpty()) {
            exercizes = originalItems
        } else {
            exercizes = originalItems.filter { it.name.contains(text, ignoreCase = true) }
        }
        sortItems()
        notifyDataSetChanged()
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