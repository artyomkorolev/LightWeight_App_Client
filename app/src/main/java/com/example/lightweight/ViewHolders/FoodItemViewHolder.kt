package com.example.lightweight.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.R
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
        calories.text = item.calories.toString()
        proteins.text = item.protein.toString()
        fats.text = item.fats.toString()
        carbs.text = item.carbohydrates.toString()
        if (hideElements) {
            save.visibility = View.GONE
            delete.visibility = View.GONE
            saveGramm.visibility = View.GONE
        }else {
            save.visibility = if (item.isSaved) View.GONE else View.VISIBLE
            delete.visibility = if (item.isSaved) View.VISIBLE else View.GONE
        }
        save.setOnClickListener {
            onSaveClick(item)
            save.visibility = View.GONE
            item.isSaved = true
            delete.visibility = View.VISIBLE
        }

        delete.setOnClickListener {
            onDeleteClick(item)
            delete.visibility = View.GONE
            item.isSaved = false
            save.visibility = View.VISIBLE
        }

        saveGramm.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                onGrammChange(item, saveGramm.text.toString())
            }
        }

    }

}