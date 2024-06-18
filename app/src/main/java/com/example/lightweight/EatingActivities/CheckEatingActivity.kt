package com.example.lightweight.EatingActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.FoodItemAdapter
import com.example.lightweight.Adapters.ProductsAdapter
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.Models.Products
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.FoodItemViewHolder
import com.example.lightweight.ViewHolders.ProductsViewHolder
import com.example.lightweight.retrofit.EatingApi
import com.example.lightweight.retrofit.FoodItemApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val products = intent.getSerializableExtra("products") as ArrayList<Products>
        val idEating = intent.getSerializableExtra("idEating").toString()

        backbutton.setOnClickListener {
            val backIntent = Intent(this, MainActivity::class.java)
            startActivity(backIntent)
        }

        deleteButton=findViewById(R.id.buttonDelete)
        deleteButton.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://212.113.121.36:8080")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val getFoodItemsService = retrofit.create(EatingApi::class.java)
            val authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcnR5b20xIiwiaWF0IjoxNzE4NjI4NTY4LCJleHAiOjE3MTkyMzMzNjh9.m4PNvxZSyLoPvZ4Aj5B4W_CPDN1lvH2SDdqQ0TsqUis"
            val call = getFoodItemsService.deleteEatingbyId(authToken,idEating)


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
            val saveIntent = Intent(this, MainActivity::class.java)
            startActivity(saveIntent)
            Toast.makeText(applicationContext,"Прием пищи удален", Toast.LENGTH_SHORT).show()
        }

        val foodItemAdapter = ProductsAdapter(
            products,
            object : ProductsAdapter.ProductsActionListener{
                override fun OnClickItem(foodItem: Products) {
                    Toast.makeText(applicationContext,"Вы нажали на продукт",Toast.LENGTH_SHORT).show()
                }
            },
            object : ProductsAdapter.OnItemClickListener{
                override fun onSaveClick(foodItem: Products,holder: ProductsViewHolder) {
                    Toast.makeText(applicationContext,"Сохранить",Toast.LENGTH_SHORT).show()
                }

                override fun onDeleteClick(foodItem: Products) {
                    Toast.makeText(applicationContext,"удалить",Toast.LENGTH_SHORT).show()
                }

                override fun onGrammChange( newGramm: String) {
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