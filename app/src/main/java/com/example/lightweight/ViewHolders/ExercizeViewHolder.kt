package com.example.lightweight.ViewHolders

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.Exercize
import com.example.lightweight.R
import com.google.android.material.textfield.TextInputEditText

class ExercizeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private val name : TextView = itemView.findViewById(R.id.exercizeName)
    private val measure : TextView = itemView.findViewById(R.id.exercizeMeasure)
    private val savefit: ImageView = itemView.findViewById(R.id.savefit)
    private val deletefit: ImageView = itemView.findViewById(R.id.deletefit)
    private val editCount: TextInputEditText = itemView.findViewById(R.id.editCount)


    fun bind(item: Exercize,
             onSaveClick: (Exercize) -> Unit,
             onDeleteClick: (Exercize) -> Unit,
             onCountChange: (Exercize, String) -> Unit,
             hideElements: Boolean = false,
             hideInputField: Boolean =false){
        name.text = item.name
        measure.text = item.unit
        editCount.setText(item.count)
        if (hideElements) {
            savefit.visibility = View.GONE
            deletefit.visibility = View.GONE
            editCount.visibility = View.GONE
            itemView.layoutParams.height = 40.dp
        }else if (hideInputField){
            editCount.visibility = View.GONE
            itemView.layoutParams.height = 40.dp
            savefit.visibility = if (item.isSaved) View.GONE else View.VISIBLE
            deletefit.visibility = if (item.isSaved) View.VISIBLE else View.GONE

        } else {
            savefit.visibility = if (item.isSaved) View.GONE else View.VISIBLE
            deletefit.visibility = if (item.isSaved) View.VISIBLE else View.GONE
            editCount.isEnabled =!item.isSaved
        }
        savefit.setOnClickListener {
            onSaveClick(item)
            if (editCount.text.isNullOrEmpty() and !hideInputField){

            }else{
            savefit.visibility = View.GONE
            item.isSaved = true
            deletefit.visibility = View.VISIBLE}
        }

        deletefit.setOnClickListener {
            onDeleteClick(item)
            deletefit.visibility = View.GONE
            item.isSaved = false
            savefit.visibility = View.VISIBLE
        }

        editCount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val newCount = editCount.text.toString()
                item.count = newCount // Сохраняем новое значение в объект
                onCountChange(item, newCount)
            }
        }

    }
    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Float.dp: Float
        get() = (this * Resources.getSystem().displayMetrics.density)

    val Double.dp: Double
        get() = (this * Resources.getSystem().displayMetrics.density).toDouble()

    fun getGrammValue(): String {
        return editCount.text.toString()
    }
}