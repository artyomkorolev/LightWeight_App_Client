package com.example.lightweight.ManagementActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.FoodItemAdapter
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.FoodItemViewHolder
import com.example.lightweight.retrofit.FoodItemApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditFoodActivity : AppCompatActivity() {
    private lateinit var backButton: Button

    private lateinit var addFoodItemButton: ImageView
    private lateinit var rvlistFoodItems: RecyclerView
    private lateinit var etSearchFood: EditText
    private lateinit var saveEatingButton: Button
    private val foodItems = ArrayList<FoodItem>()
    private lateinit var authtoken:String


    private fun deleteSelectedItems() {
        val selectedItems = foodItems.filter { it.isSaved }
        val idsToDelete = selectedItems.map { it.id }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val foodItemApi = retrofit.create(FoodItemApi::class.java)

        for (id in idsToDelete) {
            val call = foodItemApi.deleteOwnProduckt(authtoken, id)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (!response.isSuccessful) {
                        // Обработка ошибки удаления
                        // Можно показать сообщение или выполнить другие действия
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Обработка сетевой ошибки
                    // Можно показать сообщение или выполнить другие действия
                }
            })
        }

        // Удалить выбранные продукты из списка
        foodItems.removeAll(selectedItems)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_food)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"
        backButton =findViewById(R.id.backbutton)

        addFoodItemButton = findViewById(R.id.addFoodItem)
        rvlistFoodItems = findViewById(R.id.rvFoodItemList)
        etSearchFood = findViewById(R.id.imputEditText)
        saveEatingButton = findViewById(R.id.saveButton)


        addFoodItemButton.setOnClickListener{
            val addIntent = Intent(this, EditAddFoodItemActivity::class.java)
            startActivity(addIntent)
        }


        backButton.setOnClickListener{
            val backIntent= Intent(this, ManagementActivity::class.java)
            startActivity(backIntent)

        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getOwnFoodItemsService = retrofit.create(FoodItemApi::class.java)


        val foodItemAdapter = FoodItemAdapter(
            foodItems,
            object : FoodItemAdapter.FoodItemActionListener{
                override fun OnClickItem(foodItem: FoodItem) {

                }
            },
            object : FoodItemAdapter.OnItemClickListener{
                override fun onSaveClick(foodItem: FoodItem,holder: FoodItemViewHolder) {


                }

                override fun onDeleteClick(foodItem: FoodItem) {

                }

                override fun onGrammChange(foodItem: FoodItem, newGramm: String) {

                }

            }, hideInputField = true
        )

        rvlistFoodItems.adapter = foodItemAdapter

        val call  = getOwnFoodItemsService.getOwnProducts( authtoken)
        call.enqueue(object : Callback<List<FoodItem>>{
            override fun onResponse(

                call: Call<List<FoodItem>>,
                response: Response<List<FoodItem>>
            ) {
                val point = 0
                if(response.code() ==200){
                    foodItems.clear()
                    if (response.body()?.isNotEmpty() == true){
                        foodItems.addAll(response.body()!!)
                        foodItemAdapter.notifyDataSetChanged()

                    }
                }
            }

            override fun onFailure(p0: Call<List<FoodItem>>, p1: Throwable) {
                Log.e("NetworkError", "Failed to execute request", p1)
                Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()

            }

        })

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

        saveEatingButton.setOnClickListener {
            deleteSelectedItems()
            foodItemAdapter.notifyDataSetChanged()

            val backIntent= Intent(this, ManagementActivity::class.java)
            startActivity(backIntent)

        }
}}