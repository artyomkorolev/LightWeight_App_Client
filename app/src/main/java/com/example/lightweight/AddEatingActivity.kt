package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AddEatingActivity : AppCompatActivity() {

    private lateinit var backButton:Button
    private lateinit var editTextSetTime: EditText
    private lateinit var addFoodItemButton: Button
    private lateinit var rvlistFoodItems:RecyclerView
    private lateinit var etSearchFood:EditText
    private lateinit var saveEatingButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_eating)

        backButton =findViewById(R.id.backbutton)
        editTextSetTime = findViewById(R.id.etTime)
        addFoodItemButton = findViewById(R.id.addFoodItem)
        rvlistFoodItems = findViewById(R.id.rvFoodItemList)
        etSearchFood = findViewById(R.id.imputEditText)
        saveEatingButton = findViewById(R.id.saveButton)

        backButton.setOnClickListener{
            val backIntent=Intent(this, MainActivity::class.java)
            startActivity(backIntent)

        }
        val foodItemAdapter = FoodItemAdapter(
            listOf(
                FoodItem("Огурец","200","40","12","41"),
                FoodItem("Помидор","200","40","12","41"),
                FoodItem("Кукуруза","200","40","12","41"),
                FoodItem("Хлеб","200","40","12","41"),
                FoodItem("Мясо Свинина","200","40","12","41"),
                FoodItem("Мясо курицы","200","40","12","41")

            ),
            object :FoodItemAdapter.FoodItemActionListener{
                override fun OnClickItem(foodItem: FoodItem) {
                    Toast.makeText(applicationContext,"Вы нажали на продукт",Toast.LENGTH_SHORT).show()
                }
            }
        )

        rvlistFoodItems.adapter = foodItemAdapter
    }
}