package com.example.lightweight.ManagementActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lightweight.Models.Exercize
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.R
import com.example.lightweight.retrofit.ExerciseApi
import com.example.lightweight.retrofit.FoodItemApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class EditAddExercizeActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var saveButton: Button
    private lateinit var name:EditText
    private lateinit var unit:EditText
    private lateinit var authtoken:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add_exercize)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val addOwnExerciseService = retrofit.create(ExerciseApi::class.java)

        backButton = findViewById(R.id.backbutton)
        saveButton = findViewById(R.id.saveButton)
        name = findViewById(R.id.etName)
        unit = findViewById(R.id.etMeasure)


        saveButton.setOnClickListener {
            val exercizeName = name.text.toString()
            val exercizeUnit = unit.text.toString()

            val exercizeItem = Exercize(
                id = UUID.randomUUID().toString(),
                name = exercizeName,
                unit = exercizeUnit,
                count = ""

            )

            val call  = addOwnExerciseService.addOwnExercise( authtoken,exercizeItem)
            call.enqueue(object : Callback<Exercize> {
                override fun onResponse(
                    call: Call<Exercize>,
                    response: Response<Exercize>
                ) {
                    if (response.isSuccessful) {

                    } else {
                        Toast.makeText(applicationContext, "Ошибка: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(p0: Call<Exercize>, p1: Throwable) {
                    Log.e("NetworkError", "Failed to execute request", p1)
                    Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()

                }

            })
            val backIntent= Intent(this, EditExercizeActivity::class.java)
            startActivity(backIntent)

        }
        backButton.setOnClickListener {
            val backIntent= Intent(this, EditExercizeActivity::class.java)
            startActivity(backIntent)
        }


    }
}