package com.example.lightweight.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.Models.Products
import com.example.lightweight.R
import com.google.android.material.textfield.TextInputEditText

class ProductsViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    private val name: TextView =itemView.findViewById(R.id.nameItem)
    private val calories: TextView = itemView.findViewById(R.id.fooditemcal)
    private val proteins: TextView = itemView.findViewById(R.id.fooditemproteins)
    private val fats: TextView = itemView.findViewById(R.id.fooditemfats)
    private val carbs: TextView = itemView.findViewById(R.id.fooditemcarbs)
    private val save: ImageView = itemView.findViewById(R.id.save)
    private val delete: ImageView = itemView.findViewById(R.id.delete)
    private val saveGramm: TextInputEditText = itemView.findViewById(R.id.saveGramm)
    private val gramms:TextView = itemView.findViewById(R.id.gramms)

    fun bind(item: Products,
             onSaveClick: (FoodItem) -> Unit,
             onDeleteClick: (FoodItem) -> Unit,
             onGrammChange: (FoodItem, String) -> Unit,
             hideElements: Boolean = false
    ){
        name.text = item.product.name
        calories.text = item.product.calories.toString()
        proteins.text = item.product.protein.toString()
        fats.text = item.product.fats.toString()
        carbs.text = item.product.carbohydrates.toString()
        gramms.text = item.quantity.toString() +" грамм"
        if (hideElements) {
            save.visibility = View.GONE
            delete.visibility = View.GONE
            saveGramm.visibility = View.GONE
            gramms.visibility=View.VISIBLE
        }else {
            save.visibility = if (item.product.isSaved) View.GONE else View.VISIBLE
            delete.visibility = if (item.product.isSaved) View.VISIBLE else View.GONE
            saveGramm.isEnabled = !item.product.isSaved
        }
        save.setOnClickListener {

            save.visibility = View.GONE
            item.product.isSaved = true
            delete.visibility = View.VISIBLE
        }

        delete.setOnClickListener {

            delete.visibility = View.GONE
            item.product.isSaved = false
            save.visibility = View.VISIBLE
        }

        saveGramm.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {

            }
        }

    }
    fun getGrammValue(): String {
        return saveGramm.text.toString()
    }



}