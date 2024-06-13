package com.example.lightweight.ManagementActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.lightweight.R

class ManagementActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var editFood:TextView
    private lateinit var editExercize: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management)

        backButton = findViewById(R.id.backbutton)
        editFood = findViewById(R.id.editFood)
        editExercize = findViewById(R.id.editExercize)

        backButton.setOnClickListener {
            val backIntent = Intent(this, PersonalAccountActivity::class.java)
            startActivity(backIntent)

        }
        editFood.setOnClickListener {
            val foodIntent= Intent(this, EditFoodActivity::class.java)
            startActivity(foodIntent)
        }
        editExercize.setOnClickListener {
            val exIntent = Intent(this, EditExercizeActivity::class.java)
            startActivity(exIntent)
        }

    }


}