package com.example.lightweight.EatingActivities

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.FoodItemAdapter
import com.example.lightweight.Models.Eating
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.FoodItemViewHolder
import com.example.lightweight.retrofit.EatingApi
import com.example.lightweight.retrofit.FoodItemApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import java.util.Locale
import java.util.UUID

class AddEatingActivity : AppCompatActivity() {

    private lateinit var backButton:Button
    private lateinit var editTextSetTime: TextView
    private lateinit var addFoodItemButton: ImageView
    private lateinit var rvlistFoodItems:RecyclerView
    private lateinit var etSearchFood:EditText
    private lateinit var saveEatingButton:Button
    private  var searchText: String? = null
    private var products1 = ArrayList<FoodItem>()
    private var products = mutableMapOf<UUID, Double>()


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_eating)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://212.113.121.36:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getFoodItemsService = retrofit.create(FoodItemApi::class.java)
        val addEatingApiService = retrofit.create(EatingApi::class.java)

        val selectedDateMillis = intent.getLongExtra("selectedDate", 0)
        val selectedDate = Calendar.getInstance()
        selectedDate.timeInMillis = selectedDateMillis


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
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC") // Установка временной зоны UTC для корректного представления времени
            val formattedDate = dateFormat.format(selectedDate.time) // selectedDate - ваш объект Calendar или Date

            Log.d("AddEatingActivity", "Formatted Date: $formattedDate")

            // Выводим foodItemsMap в лог
            products.forEach { (key, value) ->
                Log.d("AddEatingActivity", "Food Item: Key=$key, Value=$value")
            }


            val callEating= addEatingApiService.addEating("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcnR5b20xIiwiaWF0IjoxNzE4NjI4NTY4LCJleHAiOjE3MTkyMzMzNjh9.m4PNvxZSyLoPvZ4Aj5B4W_CPDN1lvH2SDdqQ0TsqUis",
                Eating(formattedDate,products)
            )

            callEating.enqueue(object : Callback<Void>{
                override fun onResponse(
                    p0: Call<Void>,
                    p1: Response<Void>) {
                    if (p1.isSuccessful) {
                        Toast.makeText(applicationContext, "Продукт сохранен", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Ошибка: ${p1.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(p0: Call<Void>, p1: Throwable) {
                    Log.e("NetworkError", "Failed to execute request", p1)
                    Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()
                }

            })

            val backIntent=Intent(this, MainActivity::class.java)
            startActivity(backIntent)

        }
        backButton.setOnClickListener{
            val backIntent=Intent(this, MainActivity::class.java)
            startActivity(backIntent)

        }
        val foodItemAdapter = FoodItemAdapter(
            products1,
            object : FoodItemAdapter.FoodItemActionListener{
                override fun OnClickItem(foodItem: FoodItem) {
                    Toast.makeText(applicationContext,"Вы нажали на продукт",Toast.LENGTH_SHORT).show()
                }
            },
            object : FoodItemAdapter.OnItemClickListener{
                override fun onSaveClick(foodItem: FoodItem,holder: FoodItemViewHolder) {
                    val grammValue =holder.getGrammValue()
                    products[UUID.fromString(foodItem.id)]= grammValue.toDouble()


                    for ((key, value) in products) {
                        Log.d("FoodItemsMap", "Key: $key, Value: $value")
                    }

                }

                override fun onDeleteClick(foodItem: FoodItem) {
                    products.remove(UUID.fromString(foodItem.id))


                }
                override fun onGrammChange(foodItem: FoodItem, newGramm: String) {

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

        val call  = getFoodItemsService.getAllProducts( "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcnR5b20xIiwiaWF0IjoxNzE4NjI4NTY4LCJleHAiOjE3MTkyMzMzNjh9.m4PNvxZSyLoPvZ4Aj5B4W_CPDN1lvH2SDdqQ0TsqUis")
        call.enqueue(object : Callback<List<FoodItem>> {
            override fun onResponse(

                call: Call<List<FoodItem>>,
                response: Response<List<FoodItem>>
            ) {
                val point = 0
                if(response.code() ==200){
                    products1.clear()
                    if (response.body()?.isNotEmpty() == true){
                        products1.addAll(response.body()!!)
                        foodItemAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(p0: Call<List<FoodItem>>, p1: Throwable) {
                Log.e("NetworkError", "Failed to execute request", p1)
                Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()

            }

        })





    }


}