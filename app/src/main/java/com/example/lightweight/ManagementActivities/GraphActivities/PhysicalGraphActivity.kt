package com.example.lightweight.ManagementActivities.GraphActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.lightweight.Models.Exercize
import com.example.lightweight.Models.StatisticFood
import com.example.lightweight.R
import com.example.lightweight.retrofit.ExerciseApi
import com.example.lightweight.retrofit.PersonalAccApi
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhysicalGraphActivity : AppCompatActivity() {
    private lateinit var mpLineChart:LineChart
    private lateinit var backButton:Button
    private lateinit var nameExer:TextView
    private lateinit var authtoken:String
    private  var eatings =ArrayList<StatisticFood>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat_food)
        mpLineChart = findViewById(R.id.graph)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"
        val idExercize = intent.getSerializableExtra("idExervc").toString()
        val nameExercize = intent.getSerializableExtra("nameExervc").toString()
        nameExer =findViewById(R.id.nameFood)
        nameExer.text = "Статистика по упражнению\n ${nameExercize}"
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getFoodItemsService = retrofit.create(PersonalAccApi::class.java)
        val call = getFoodItemsService.getStatExerciseById(authtoken,idExercize)
        call.enqueue(object : Callback<ArrayList<StatisticFood>> {
            override fun onResponse(
                call: Call<ArrayList<StatisticFood>>,
                response: Response<ArrayList<StatisticFood>>
            ) {
                if (response.isSuccessful) {
                    eatings.clear()
                    eatings.addAll(response.body()!!)

                    val entries = ArrayList<Entry>()
                    for ((index, statFood) in eatings.withIndex()) {
                        entries.add(Entry(index.toFloat(), statFood.statValue.toFloat()))
                    }
                    val type = eatings.get(eatings.size-1).statType
                    val dataSet = LineDataSet(entries,type ) // Создаем набор данных графика
                    dataSet.circleRadius = 8f

                    val lineData = LineData(dataSet) // Создаем объект LineData, содержащий набор данных

                    mpLineChart.data = lineData // Устанавливаем данные графика
                    // Настройка меток и форматирования для оси X
                    mpLineChart.xAxis.valueFormatter = object : ValueFormatter() {
                        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                            val date = eatings[value.toInt()].date
                            return date.substring(5)
                        }
                    }

                    // Настройка меток и форматирования для оси Y
                    mpLineChart.axisLeft.setLabelCount(5, true) // Устанавливаем количество меток на оси Y
                    mpLineChart.axisLeft.valueFormatter = object : ValueFormatter() {
                        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                            return "${value.toInt()} $type" // Добавляем тип статистики к числу и возвращаем как строку
                        }
                    }

                    mpLineChart.axisRight.isEnabled = false
                    mpLineChart.legend.isEnabled = false
                    mpLineChart.invalidate() // Обновляем график

                } else {
                    Toast.makeText(applicationContext, "Network Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(p0: Call<ArrayList<StatisticFood>>, p1: Throwable) {
                Log.e("NetworkError", "Failed to execute request", p1)
                Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()

            }

        })

        mpLineChart.axisRight.isEnabled = false
        mpLineChart.legend.isEnabled = false
        mpLineChart.invalidate() // Обновляем график

        backButton=findViewById(R.id.backbutton)
        backButton.setOnClickListener {
            val backIntent = Intent(this, StatFKActivity::class.java)
            startActivity(backIntent)
        }
    }


}