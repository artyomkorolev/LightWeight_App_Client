package com.example.lightweight.ViewHolders

import android.app.AlertDialog
import android.content.res.Resources
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
    private val gramms:TextView = itemView.findViewById(R.id.gramms)


    fun bind(item: FoodItem,
             onSaveClick: (FoodItem) -> Unit,
             onDeleteClick: (FoodItem) -> Unit,
             onGrammChange: (FoodItem, String) -> Unit,
             hideElements: Boolean = false,
             hideInputField: Boolean =false
             ){
        name.text = item.name
        calories.text = item.calories.toString()
        proteins.text = item.protein.toString()
        fats.text = item.fats.toString()
        carbs.text = item.carbohydrates.toString()
        saveGramm.setText(item.count)
        if (hideElements) {
            save.visibility = View.GONE
            delete.visibility = View.GONE
            saveGramm.visibility = View.GONE
            gramms.visibility=View.VISIBLE
        }else if (hideInputField){
            saveGramm.visibility = View.GONE
            itemView.layoutParams.height = 60.dp
            save.visibility = if (item.isSaved) View.GONE else View.VISIBLE
            delete.visibility = if (item.isSaved) View.VISIBLE else View.GONE

        } else {
            save.visibility = if (item.isSaved) View.GONE else View.VISIBLE
            delete.visibility = if (item.isSaved) View.VISIBLE else View.GONE
            saveGramm.isEnabled = !item.isSaved
        }
        save.setOnClickListener {
            onSaveClick(item)
            if (saveGramm.text.isNullOrEmpty() and !hideInputField){

            }else{

            save.visibility = View.GONE
            item.isSaved = true
            delete.visibility = View.VISIBLE}
        }

        delete.setOnClickListener {
            onDeleteClick(item)
            delete.visibility = View.GONE
            item.isSaved = false
            save.visibility = View.VISIBLE
        }

        saveGramm.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val newCount = saveGramm.text.toString()
                item.count = newCount // Сохраняем новое значение в объект
                onGrammChange(item, newCount)
            }
        }

    }
    fun getGrammValue(): String {
        return saveGramm.text.toString()
    }
    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()


}