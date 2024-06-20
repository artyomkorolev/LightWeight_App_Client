package com.example.lightweight.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.Exersise
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.ExersisesViewHolder

class ExersisesAdapter (private var exercizes:List<Exersise>, private val exercizeActionListener: ExercizeActionListener,
                        private val onItemClickListener: OnItemClickListener,
                        private val hideElements: Boolean = false
): RecyclerView.Adapter<ExersisesViewHolder>() {
    private var originalItems: List<Exersise> = exercizes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExersisesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.physical_position_item,parent,false)
        return ExersisesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exercizes.size
    }

    override fun onBindViewHolder(holder: ExersisesViewHolder, position: Int) {

        val exercizeItem = exercizes[position]
        holder.bind(
            exercizeItem,
            { onItemClickListener.onSaveClick(exercizeItem,holder)
                hideKeyboard(holder.itemView.context, holder.itemView)

                sortItems()
                notifyDataSetChanged()
            },
            { onItemClickListener.onDeleteClick(exercizeItem)

                sortItems()
                notifyDataSetChanged()
            },
            { item, newCount -> onItemClickListener.onGrammChange( newCount) },
            hideElements
        )
        holder.itemView.setOnClickListener{
            exercizeActionListener.OnClickItem(exercizes[position])
        }
    }
    private fun sortItems() {
        exercizes = exercizes.sortedByDescending { it.exercise.isSaved }
    }
    fun filterItems(text: String) {
        if (text.isEmpty()) {
            exercizes = originalItems
        } else {
            exercizes = originalItems.filter { it.exercise.name.contains(text, ignoreCase = true) }
        }
        sortItems()
        notifyDataSetChanged()
    }
    interface ExercizeActionListener{
        fun OnClickItem(exercize: Exersise)
    }
    interface OnItemClickListener {
        fun onSaveClick(exercize: Exersise, holder: ExersisesViewHolder)
        fun onDeleteClick(exercize: Exersise)
        fun onGrammChange( newCount: String)
    }
    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}