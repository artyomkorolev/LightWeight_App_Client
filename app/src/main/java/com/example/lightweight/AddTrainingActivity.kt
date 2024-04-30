package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.time.Duration

class AddTrainingActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var rvExercizeList:RecyclerView
    private lateinit var etTime:EditText
    private lateinit var etDuration: EditText
    private lateinit var saveButton: Button
    private lateinit var addExercizeButton: ImageView
    private lateinit var etSearchExercize: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_training)

        backButton = findViewById(R.id.backbutton)
        rvExercizeList = findViewById(R.id.rvExercizeList)
        etTime = findViewById(R.id.etTime)
        etDuration= findViewById(R.id.etDuration)
        saveButton = findViewById(R.id.saveButton)
        addExercizeButton = findViewById(R.id.addExercize)
        etSearchExercize = findViewById(R.id.imputEditText)

        addExercizeButton.setOnClickListener {
            val exercizeIntent = Intent(this, AddExerciseActivity::class.java)
            startActivity(exercizeIntent)
        }


        backButton.setOnClickListener {
            val backIntent=Intent(this,ActivityPhysical::class.java)
            startActivity(backIntent)
        }
        saveButton.setOnClickListener {
            val backIntent=Intent(this,ActivityPhysical::class.java)
            startActivity(backIntent)
            Toast.makeText(applicationContext,"Тренировка добавлена", Toast.LENGTH_SHORT).show()
        }

        val exercizeAdapter = ExercizeAdapter(
            listOf(
                Exercize("Бег","км"),
                Exercize("Подтягивания","шт"),
                Exercize("Жим лежа","шт")

            ),
            object : ExercizeAdapter.ExercizeActionListener{
                override fun OnClickItem(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Вы нажали на упражнение",Toast.LENGTH_SHORT).show()
                }
            }
        )
        rvExercizeList.adapter=exercizeAdapter

    }
}