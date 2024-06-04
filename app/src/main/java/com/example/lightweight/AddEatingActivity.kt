package com.example.lightweight

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class AddEatingActivity : AppCompatActivity() {

    private lateinit var backButton:Button
    private lateinit var editTextSetTime: TextView
    private lateinit var addFoodItemButton: ImageView
    private lateinit var rvlistFoodItems:RecyclerView
    private lateinit var etSearchFood:EditText
    private lateinit var saveEatingButton:Button
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_eating)

        backButton =findViewById(R.id.backbutton)
        editTextSetTime = findViewById(R.id.etTime)
        addFoodItemButton = findViewById(R.id.addFoodItem)
        rvlistFoodItems = findViewById(R.id.rvFoodItemList)
        etSearchFood = findViewById(R.id.imputEditText)
        saveEatingButton = findViewById(R.id.saveButton)


        addFoodItemButton.setOnClickListener{
            val addIntent = Intent(this,AddFoodItemActivity::class.java)
            startActivity(addIntent)
        }

        saveEatingButton.setOnClickListener {
            val backIntent=Intent(this, MainActivity::class.java)
            startActivity(backIntent)
            Toast.makeText(applicationContext,"Вы сохранили прием пищи",Toast.LENGTH_SHORT).show()
        }
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

        val formatDate = SimpleDateFormat("HH:mm", Locale("ru"))
        editTextSetTime.text =formatDate.format(Calendar.getInstance().time)
            editTextSetTime.setOnClickListener {
            val getDate = Calendar.getInstance()
            val timePicker = TimePickerDialog(
                this, // Убедитесь, что используете правильный контекст
                { _, hourOfDay, minute ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedDate.set(Calendar.MINUTE, minute)
                    val formatDate = SimpleDateFormat("HH:mm", Locale("ru"))
                    val formattedTime = formatDate.format(selectedDate.time)
                    editTextSetTime.text = formattedTime // Устанавливаем выбранное время в EditText
                },
                getDate.get(Calendar.HOUR_OF_DAY), // Используйте HOUR_OF_DAY для 24-часового формата
                getDate.get(Calendar.MINUTE),
                true // Установите 'true' для 24-часового формата времени
            )
            timePicker.show()
        }
    }
}