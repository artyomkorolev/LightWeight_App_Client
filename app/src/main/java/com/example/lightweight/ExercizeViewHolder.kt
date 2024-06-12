package com.example.lightweight

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class ExercizeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private val name : TextView = itemView.findViewById(R.id.exercizeName)
    private val measure : TextView = itemView.findViewById(R.id.exercizeMeasure)
    private val savefit: ImageView = itemView.findViewById(R.id.savefit)
    private val deletefit: ImageView = itemView.findViewById(R.id.deletefit)
    private val editCount: TextInputEditText = itemView.findViewById(R.id.editCount)


    fun bind(item:Exercize,
             onSaveClick: (Exercize) -> Unit,
             onDeleteClick: (Exercize) -> Unit,
             onCountChange: (Exercize, String) -> Unit,
             hideElements: Boolean = false){
        name.text = item.name
        measure.text = item.measure
        if (hideElements) {
            savefit.visibility = View.GONE
            deletefit.visibility = View.GONE
            editCount.visibility = View.GONE
        }else {
            savefit.visibility = if (item.isSaved) View.GONE else View.VISIBLE
            deletefit.visibility = if (item.isSaved) View.VISIBLE else View.GONE
        }
        savefit.setOnClickListener {
            onSaveClick(item)
            savefit.visibility = View.GONE
            deletefit.visibility = View.VISIBLE
        }

        deletefit.setOnClickListener {
            onDeleteClick(item)
            deletefit.visibility = View.GONE
            savefit.visibility = View.VISIBLE
        }

        editCount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                onCountChange(item, editCount.text.toString())
            }
        }

    }
}