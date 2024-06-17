package com.example.lightweight.EatingActivities

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.FoodItemAdapter
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.R
import java.util.Locale

class AddEatingActivity : AppCompatActivity() {

    private lateinit var backButton:Button
    private lateinit var editTextSetTime: TextView
    private lateinit var addFoodItemButton: ImageView
    private lateinit var rvlistFoodItems:RecyclerView
    private lateinit var etSearchFood:EditText
    private lateinit var saveEatingButton:Button
    private  var searchText: String? = null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_eating)

        backButton =findViewById(R.id.backbutton)
        editTextSetTime = findViewById(R.id.etTime)
        addFoodItemButton = findViewById(R.id.addFoodItem)
        rvlistFoodItems = findViewById(R.id.rvFoodItemList)

        saveEatingButton = findViewById(R.id.saveButton)


        addFoodItemButton.setOnClickListener{
            val addIntent = Intent(this, AddFoodItemActivity::class.java)
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
                FoodItem("1","Огурец",200, 40.0, 12.0, 41.0),
                FoodItem("1","Помидор",200, 40.0, 12.0, 41.0),
                FoodItem("1","Кукуруза",200, 40.0, 12.0, 41.0),
                FoodItem("1","Хлеб",200, 40.0, 12.0, 41.0),
                FoodItem("1","Мясо Свинина",200, 40.0, 12.0, 41.0),
                FoodItem("1","Мясо курицы",200, 40.0, 12.0, 41.0)

            ),
            object : FoodItemAdapter.FoodItemActionListener{
                override fun OnClickItem(foodItem: FoodItem) {
                    Toast.makeText(applicationContext,"Вы нажали на продукт",Toast.LENGTH_SHORT).show()
                }
            },
            object : FoodItemAdapter.OnItemClickListener{
                override fun onSaveClick(foodItem: FoodItem) {
                    Toast.makeText(applicationContext,"Сохранить",Toast.LENGTH_SHORT).show()
                }

                override fun onDeleteClick(foodItem: FoodItem) {
                    Toast.makeText(applicationContext,"удалить",Toast.LENGTH_SHORT).show()
                }
                override fun onGrammChange(foodItem: FoodItem, newGramm: String) {
                    Toast.makeText(applicationContext,"текст",Toast.LENGTH_SHORT).show()
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

        etSearchFood = findViewById(R.id.imputEditText)
        val textWatcher = object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                        searchText ->
                    foodItemAdapter.filterItems(searchText.toString())
                }
            }

        }

        etSearchFood.addTextChangedListener(textWatcher)

    }
}