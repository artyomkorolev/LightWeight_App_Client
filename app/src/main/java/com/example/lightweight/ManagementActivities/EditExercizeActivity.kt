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
import com.example.lightweight.Adapters.ExercizeAdapter
import com.example.lightweight.Models.Exercize
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.ExercizeViewHolder
import com.example.lightweight.ViewHolders.ExersisesViewHolder
import com.example.lightweight.retrofit.ExerciseApi
import com.example.lightweight.retrofit.FoodItemApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditExercizeActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var rvExercizeList: RecyclerView
    private lateinit var etTime: EditText
    private lateinit var etDuration: EditText
    private lateinit var saveButton: Button
    private lateinit var addExercizeButton: ImageView
    private lateinit var etSearchExercize: EditText
    private val exercises = ArrayList<Exercize>()
    private lateinit var authtoken:String
    private fun deleteSelectedItems() {
        val selectedItems = exercises.filter { it.isSaved }
        val idsToDelete = selectedItems.map { it.id }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val exerciseApi = retrofit.create(ExerciseApi::class.java)

        for (id in idsToDelete) {
            val call = exerciseApi.deleteOwnExercise("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcnR5b20xIiwiaWF0IjoxNzE4NjI4NTY4LCJleHAiOjE3MTkyMzMzNjh9.m4PNvxZSyLoPvZ4Aj5B4W_CPDN1lvH2SDdqQ0TsqUis", id)
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
        exercises.removeAll(selectedItems)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_exercize)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"

        backButton = findViewById(R.id.backbutton)
        rvExercizeList = findViewById(R.id.rvExercizeList)
        saveButton = findViewById(R.id.saveButton)
        addExercizeButton = findViewById(R.id.addExercize)
        etSearchExercize = findViewById(R.id.imputEditText)

        addExercizeButton.setOnClickListener {
            val exercizeIntent = Intent(this, EditAddExercizeActivity::class.java)
            startActivity(exercizeIntent)
        }


        backButton.setOnClickListener {
            val backIntent= Intent(this, ManagementActivity::class.java)
            startActivity(backIntent)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getOwnExercisesService = retrofit.create(ExerciseApi::class.java)





        val exercizeAdapter = ExercizeAdapter(
            exercises,
            object : ExercizeAdapter.ExercizeActionListener{
                override fun OnClickItem(exercize: Exercize) {

                }
            }, object : ExercizeAdapter.OnItemClickListener{
                override fun onSaveClick(exercize: Exercize,holder: ExercizeViewHolder) {

                }

                override fun onDeleteClick(exercize: Exercize) {

                }

                override fun onGrammChange(exercize: Exercize, newCount: String) {

                }

            }, hideInputField = true
        )
        rvExercizeList.adapter=exercizeAdapter

        val call  =getOwnExercisesService.getOwnExercise ( authtoken)
        call.enqueue(object : Callback<List<Exercize>> {
            override fun onResponse(

                call: Call<List<Exercize>>,
                response: Response<List<Exercize>>
            ) {
                val point = 0
                if(response.code() ==200){
                    exercises.clear()
                    if (response.body()?.isNotEmpty() == true){
                        exercises.addAll(response.body()!!)
                        exercizeAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(p0: Call<List<Exercize>>, p1: Throwable) {
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
                    exercizeAdapter.filterItems(searchText.toString())
                }
            }

        }

        etSearchExercize.addTextChangedListener(textWatcher)


        saveButton.setOnClickListener {
            deleteSelectedItems()
            exercizeAdapter.notifyDataSetChanged()
            val backIntent= Intent(this, ManagementActivity::class.java)
            startActivity(backIntent)
            Toast.makeText(applicationContext,"Список упраженений сохранен", Toast.LENGTH_SHORT).show()
        }

    }
}