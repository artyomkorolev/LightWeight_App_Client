package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ActivityPhysical : AppCompatActivity() {
    private lateinit var addTrainingButton: ImageView
    //BOTTOMBAR
    private lateinit var buttonFood: Button
    private lateinit var buttonGallery: Button
    private lateinit var buttonLK: Button

    private lateinit var tvdate:TextView
    private lateinit var rvTrainingList:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physical)

        rvTrainingList = findViewById(R.id.rvPhysicalList)
        tvdate = findViewById(R.id.tvDate)
        addTrainingButton = findViewById(R.id.addTraining)
        buttonFood=findViewById(R.id.buttonFood)
        buttonGallery = findViewById(R.id.buttonGallery)
        buttonLK = findViewById(R.id.buttonLK)

        buttonFood.setOnClickListener{
            val fkIntent = Intent(this, MainActivity::class.java)
            startActivity(fkIntent)
        }
        buttonGallery.setOnClickListener{
            val galIntent = Intent(this, GalleryActivity::class.java)
            startActivity(galIntent)
        }
        buttonLK.setOnClickListener{
            val galIntent = Intent(this, PersonalAccountActivity::class.java)
            startActivity(galIntent)
        }
        addTrainingButton.setOnClickListener{
            val addIntent = Intent(this, AddTrainingActivity::class.java)
            startActivity(addIntent)
        }

        val trainingAdapter = TrainingAdapter(
            listOf(
                Training("13:25","45"),
                Training("15:25","90"),
                Training("17:25","45"),
                Training("19:25","45"),
                Training("21:25","45"),
                Training("23:25","45")

            ),
            object : TrainingAdapter.TrainingActionListener{
                override fun OnClickItem(training: Training) {
                    val checkIntent = Intent(this@ActivityPhysical,CheckTrainingActivity::class.java)
                    startActivity(checkIntent)
                }
            }

        )
        rvTrainingList.adapter = trainingAdapter



    }
}