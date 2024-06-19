package com.example.lightweight.PhysicalActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.ExercizeAdapter
import com.example.lightweight.Adapters.ExersisesAdapter
import com.example.lightweight.Models.Exercize
import com.example.lightweight.Models.Exersise
import com.example.lightweight.Models.Products
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.ExersisesViewHolder
import com.example.lightweight.retrofit.EatingApi
import com.example.lightweight.retrofit.WorkoutApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CheckTrainingActivity : AppCompatActivity() {
    private lateinit var backbutton:Button
    private lateinit var deleteButton:Button
    private lateinit var rvExercizeList:RecyclerView
    private lateinit var authtoken:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_training)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"

        backbutton=findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val backIntent = Intent(this, ActivityPhysical::class.java)
            startActivity(backIntent)
        }
        val exesises = intent.getSerializableExtra("exesises") as ArrayList<Exersise>
        val idTraining = intent.getSerializableExtra("idTraining").toString()

        deleteButton=findViewById(R.id.buttonDelete)
        deleteButton.setOnClickListener {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://light-weight.site:8080")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val getFoodItemsService = retrofit.create(WorkoutApi::class.java)

            val call = getFoodItemsService.deleteWorkoutbyId(authtoken,idTraining)


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

            val saveIntent = Intent(this, ActivityPhysical::class.java)
            startActivity(saveIntent)


        }
        rvExercizeList =findViewById(R.id.rvExercizeListCheck)
        val exercizeAdapter = ExersisesAdapter(
            exesises,
            object : ExersisesAdapter.ExercizeActionListener{
                override fun OnClickItem(exercize: Exersise) {

                }
            }, object : ExersisesAdapter.OnItemClickListener{
                override fun onSaveClick(exercize: Exersise,holder: ExersisesViewHolder) {

                }

                override fun onDeleteClick(exercize: Exersise) {

                }

                override fun onGrammChange( newCount: String) {

                }

            },hideElements = true
        )
        rvExercizeList.adapter=exercizeAdapter
    }
}