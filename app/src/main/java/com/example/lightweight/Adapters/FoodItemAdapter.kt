package com.example.lightweight.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.ViewHolders.FoodItemViewHolder
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.R
import com.google.android.material.internal.ViewUtils.hideKeyboard

class FoodItemAdapter(private var fooditems: List<FoodItem>, private val foodItemActionListener: FoodItemActionListener,
                      private val onItemClickListener: OnItemClickListener,
                      private val hideElements: Boolean = false,
                      private val hideInputField: Boolean = false
                        ):RecyclerView.Adapter<FoodItemViewHolder>() {
    private var originalItems: List<FoodItem> = fooditems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_position_item,parent,false)

        return FoodItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fooditems.size
    }


    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val foodItem = fooditems[position]
        holder.bind(
            foodItem,
            { onItemClickListener.onSaveClick(foodItem,holder)
                if (holder.getGrammValue().isNullOrEmpty()){}else{
                    hideKeyboard(holder.itemView.context,holder.itemView)
                    foodItem.isSaved = true
                }


            sortItems()
            notifyDataSetChanged()
            },
            { onItemClickListener.onDeleteClick(foodItem)
                foodItem.isSaved = false
                sortItems()
            notifyDataSetChanged()
            },
            { item, newGramm -> onItemClickListener.onGrammChange(item, newGramm) },
            hideElements,
            hideInputField
        )

        holder.itemView.setOnClickListener{
            foodItemActionListener.OnClickItem(fooditems[position])
        }

    }
    fun updateItems(newItems: List<FoodItem>) {
        fooditems = newItems
        notifyDataSetChanged()
    }

    private fun sortItems() {
        fooditems = fooditems.sortedByDescending { it.isSaved }
    }
    fun filterItems(text: String) {
        if (text.isEmpty()) {
            fooditems = originalItems
        } else {
            fooditems = originalItems.filter { it.name.contains(text, ignoreCase = true) }
        }
        sortItems()
        notifyDataSetChanged()
    }

    interface FoodItemActionListener{
        fun OnClickItem(foodItem: FoodItem)    }
    interface OnItemClickListener {
        fun onSaveClick(foodItem: FoodItem,holder: FoodItemViewHolder)
        fun onDeleteClick(foodItem: FoodItem)
        fun onGrammChange(foodItem: FoodItem, newGramm: String)
    }
    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}