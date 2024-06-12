package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CheckEatingActivity : AppCompatActivity() {
    private lateinit var backbutton:Button
    private lateinit var deleteButton:Button
    private lateinit var rvlistFoodItems: RecyclerView
    private lateinit var etSearchFood: EditText
    private var searchText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_eating)
        rvlistFoodItems = findViewById(R.id.rvFoodItemList)
        backbutton=findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val backIntent = Intent(this,MainActivity::class.java)
            startActivity(backIntent)
        }

        deleteButton=findViewById(R.id.buttonDelete)
        deleteButton.setOnClickListener {

            val saveIntent = Intent(this,MainActivity::class.java)
            startActivity(saveIntent)
            Toast.makeText(applicationContext,"Прием пищи удален", Toast.LENGTH_SHORT).show()

        }

        val foodItemAdapter = FoodItemAdapter(
            listOf(
                FoodItem("Огурец","200","40","12","41"),
                FoodItem("Помидор","200","40","12","41"),
                FoodItem("Кукуруза","200","40","12","41"),

            ),
            object :FoodItemAdapter.FoodItemActionListener{
                override fun OnClickItem(foodItem: FoodItem) {
                    Toast.makeText(applicationContext,"Вы нажали на продукт",Toast.LENGTH_SHORT).show()
                }
            },
            object :FoodItemAdapter.OnItemClickListener{
                override fun onSaveClick(foodItem: FoodItem) {
                    Toast.makeText(applicationContext,"Сохранить",Toast.LENGTH_SHORT).show()
                }

                override fun onDeleteClick(foodItem: FoodItem) {
                    Toast.makeText(applicationContext,"удалить",Toast.LENGTH_SHORT).show()
                }

                override fun onGrammChange(foodItem: FoodItem, newGramm: String) {
                    Toast.makeText(applicationContext,"текст",Toast.LENGTH_SHORT).show()
                }

            },hideElements = true
        )

        rvlistFoodItems.adapter = foodItemAdapter

        etSearchFood = findViewById(R.id.imputEditText)
        val textWatcher = object : TextWatcher {
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