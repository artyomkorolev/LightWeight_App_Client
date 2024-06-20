package com.example.lightweight.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.Models.Products
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.FoodItemViewHolder
import com.example.lightweight.ViewHolders.ProductsViewHolder

class ProductsAdapter(private var fooditems: List<Products>, private val foodItemActionListener: ProductsActionListener,
                      private val onItemClickListener: OnItemClickListener,
                      private val hideElements: Boolean = false):
    RecyclerView.Adapter<ProductsViewHolder>() {
    private var originalItems: List<Products> = fooditems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_position_item,parent,false)

        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fooditems.size
    }


    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val foodItem = fooditems[position]
        holder.bind(
            foodItem,
            { onItemClickListener.onSaveClick(foodItem,holder)
                foodItem.product.isSaved = true
                sortItems()
                notifyDataSetChanged()
            },
            { onItemClickListener.onDeleteClick(foodItem)
                foodItem.product.isSaved = false
                sortItems()
                notifyDataSetChanged()
            },
            { item, newGramm -> onItemClickListener.onGrammChange(newGramm) },
            hideElements
        )

        holder.itemView.setOnClickListener{
            foodItemActionListener.OnClickItem(fooditems[position])
        }

    }
    fun updateItems(newItems: List<Products>) {
        fooditems = newItems
        notifyDataSetChanged()
    }

    private fun sortItems() {
        fooditems = fooditems.sortedByDescending { it.product.isSaved }
    }
    fun filterItems(text: String) {
        if (text.isEmpty()) {
            fooditems = originalItems
        } else {
            fooditems = originalItems.filter { it.product.name.contains(text, ignoreCase = true) }
        }
        sortItems()
        notifyDataSetChanged()
    }

    interface ProductsActionListener{
        fun OnClickItem(foodItem: Products)    }
    interface OnItemClickListener {
        fun onSaveClick(foodItem: Products, holder: ProductsViewHolder)
        fun onDeleteClick(foodItem: Products)
        fun onGrammChange( newGramm: String)
    }


}