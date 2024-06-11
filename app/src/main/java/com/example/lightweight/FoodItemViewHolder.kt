package com.example.lightweight

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class FoodItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val name: TextView =itemView.findViewById(R.id.nameItem)
    private val calories:TextView = itemView.findViewById(R.id.fooditemcal)
    private val proteins:TextView = itemView.findViewById(R.id.fooditemproteins)
    private val fats:TextView = itemView.findViewById(R.id.fooditemfats)
    private val carbs:TextView = itemView.findViewById(R.id.fooditemcarbs)
    private val save: ImageView = itemView.findViewById(R.id.save)
    private val delete: ImageView = itemView.findViewById(R.id.delete)
    private val saveGramm: TextInputEditText = itemView.findViewById(R.id.saveGramm)


    fun bind(item: FoodItem,
             onSaveClick: (FoodItem) -> Unit,
             onDeleteClick: (FoodItem) -> Unit,
             onGrammChange: (FoodItem, String) -> Unit,
             hideElements: Boolean = false
             ){
        name.text = item.name
        calories.text = item.calories
        proteins.text = item.proteins
        fats.text = item.fats
        carbs.text = item.Carbs
        if (hideElements) {
            save.visibility = View.GONE
            delete.visibility = View.GONE
            saveGramm.visibility = View.GONE
        }
        save.setOnClickListener {
            onSaveClick(item)
            save.visibility = View.GONE
            delete.visibility = View.VISIBLE
        }

        delete.setOnClickListener {
            onDeleteClick(item)
            delete.visibility = View.GONE
            save.visibility = View.VISIBLE
        }

        saveGramm.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                onGrammChange(item, saveGramm.text.toString())
            }
        }

    }

}