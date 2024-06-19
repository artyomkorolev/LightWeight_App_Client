package com.example.lightweight.ViewHolders

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Models.Exercize
import com.example.lightweight.Models.Exersise
import com.example.lightweight.R
import com.google.android.material.textfield.TextInputEditText

class ExersisesViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    private val name : TextView = itemView.findViewById(R.id.exercizeName)
    private val measure : TextView = itemView.findViewById(R.id.exercizeMeasure)
    private val savefit: ImageView = itemView.findViewById(R.id.savefit)
    private val deletefit: ImageView = itemView.findViewById(R.id.deletefit)
    private val editCount: TextInputEditText = itemView.findViewById(R.id.editCount)
    private val counts: TextView = itemView.findViewById(R.id.finalCount)

    fun bind(item: Exersise,
             onSaveClick: (Exercize) -> Unit,
             onDeleteClick: (Exercize) -> Unit,
             onCountChange: (Exercize, String) -> Unit,
             hideElements: Boolean = false){
        if (item.exercise != null) {
            name.text = item.exercise.name
            measure.text = item.exercise.unit
        } else {
            name.text = "Unknown exercise"
            measure.text = "Unknown unit"
        }
        counts.text = item.quantity.toString()
        if (hideElements) {
            savefit.visibility = View.GONE
            deletefit.visibility = View.GONE
            editCount.visibility = View.GONE
            counts.visibility = View.VISIBLE
        }else {
            savefit.visibility = if (item.exercise.isSaved) View.GONE else View.VISIBLE
            deletefit.visibility = if (item.exercise.isSaved) View.VISIBLE else View.GONE
            editCount.isEnabled =!item.exercise.isSaved
        }
        savefit.setOnClickListener {

            savefit.visibility = View.GONE
            deletefit.visibility = View.VISIBLE
        }

        deletefit.setOnClickListener {

            deletefit.visibility = View.GONE
            savefit.visibility = View.VISIBLE
        }

        editCount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {

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