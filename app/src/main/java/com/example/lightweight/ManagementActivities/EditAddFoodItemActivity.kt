package com.example.lightweight.ManagementActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.R
import com.example.lightweight.retrofit.FoodItemApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class EditAddFoodItemActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var saveButton: Button
    private lateinit var name: EditText
    private lateinit var calories: EditText
    private lateinit var proteins: EditText
    private lateinit var fats:EditText
    private lateinit var carbs: EditText
    private lateinit var authtoken:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add_food_item)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getOwnFoodItemsService = retrofit.create(FoodItemApi::class.java)

        backButton = findViewById(R.id.backbutton)
        saveButton = findViewById(R.id.saveButton)


        name = findViewById(R.id.etnamefood)
        calories = findViewById(R.id.etCals)
        proteins = findViewById(R.id.etProteins)
        fats = findViewById(R.id.etFats)
        carbs = findViewById(R.id.etCarbs)




        saveButton.setOnClickListener {

            val foodName = name.text.toString()
            val foodCalories = calories.text.toString().toIntOrNull() ?: 0
            val foodProteins = proteins.text.toString().toDoubleOrNull() ?: 0
            val foodFats = fats.text.toString().toDoubleOrNull() ?: 0
            val foodCarbs = carbs.text.toString().toDoubleOrNull() ?: 0

            // Создание объекта FoodItem
            val foodItem = FoodItem(
                id = UUID.randomUUID().toString(),
                name = foodName,
                calories = foodCalories,
                protein = foodProteins.toDouble(),
                fats = foodFats.toDouble(),
                carbohydrates = foodCarbs.toDouble(),
                count = ""
            )



            val call  = getOwnFoodItemsService.addOwnProduct( authtoken,foodItem)
            call.enqueue(object : Callback<FoodItem>{
                override fun onResponse(
                    call: Call<FoodItem>,
                    response: Response<FoodItem>) {
                    if (response.isSuccessful) {

                    } else {
                        Toast.makeText(applicationContext, "Ошибка: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(p0: Call<FoodItem>, p1: Throwable) {
                    Log.e("NetworkError", "Failed to execute request", p1)
                    Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()

                }

            })
            val backIntent= Intent(this, EditFoodActivity::class.java)
            startActivity(backIntent)

        }
        backButton.setOnClickListener {
            val backIntent= Intent(this, EditFoodActivity::class.java)
            startActivity(backIntent)
        }








}}