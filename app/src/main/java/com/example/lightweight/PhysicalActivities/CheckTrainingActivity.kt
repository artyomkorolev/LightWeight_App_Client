package com.example.lightweight.PhysicalActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.ExercizeAdapter
import com.example.lightweight.Models.Exercize
import com.example.lightweight.R

class CheckTrainingActivity : AppCompatActivity() {
    private lateinit var backbutton:Button
    private lateinit var deleteButton:Button
    private lateinit var rvExercizeList:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_training)
        backbutton=findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val backIntent = Intent(this, ActivityPhysical::class.java)
            startActivity(backIntent)
        }

        deleteButton=findViewById(R.id.buttonDelete)
        deleteButton.setOnClickListener {

            val saveIntent = Intent(this, ActivityPhysical::class.java)
            startActivity(saveIntent)
            Toast.makeText(applicationContext,"Тренировка удалена", Toast.LENGTH_SHORT).show()

        }
        rvExercizeList =findViewById(R.id.rvExercizeListCheck)
        val exercizeAdapter = ExercizeAdapter(
            listOf(
                Exercize("1","Бег","км"),
                Exercize("1","Подтягивания","шт"),
                Exercize("1","Жим лежа","шт")

            ),
            object : ExercizeAdapter.ExercizeActionListener{
                override fun OnClickItem(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Вы нажали на упражнение",Toast.LENGTH_SHORT).show()
                }
            }, object : ExercizeAdapter.OnItemClickListener{
                override fun onSaveClick(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Сохранить",Toast.LENGTH_SHORT).show()
                }

                override fun onDeleteClick(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Удалить",Toast.LENGTH_SHORT).show()
                }

                override fun onGrammChange(exercize: Exercize, newCount: String) {
                    Toast.makeText(applicationContext,"Изменить",Toast.LENGTH_SHORT).show()
                }

            },hideElements = true
        )
        rvExercizeList.adapter=exercizeAdapter
    }
}