package com.example.lightweight

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar
import java.util.Locale

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

        val dateFormat = SimpleDateFormat("EEEE, d MMM", Locale("ru"),)

        tvdate.text = dateFormat.format(Calendar.getInstance().time)
        tvdate.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->

                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("EEEE, d MMM", Locale("ru"),)
                    val formattedDate = dateFormat.format(selectedDate.time)

                    val tvDate = findViewById<TextView>(R.id.tvDate)
                    tvDate.text = formattedDate // Устанавливаем отформатированную дату в TextView

                },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

    }
}