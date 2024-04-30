package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class StatFoodActivity : AppCompatActivity() {
    private lateinit var mpLineChart: LineChart
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat_food)
        mpLineChart = findViewById(R.id.graph)

        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 2000f)) // Пример точки на графике: x = 1, y = 50
        entries.add(Entry(2f, 1500f)) // Пример точки на графике: x = 2, y = 60
        entries.add(Entry(3f, 2500f)) // Пример точки на графике: x = 3, y = 70

        val dataSet = LineDataSet(entries, null) // Создаем набор данных графика
        dataSet.circleRadius = 8f

        val lineData = LineData(dataSet) // Создаем объект LineData, содержащий набор данных

        mpLineChart.data = lineData // Устанавливаем данные графика
        // Настройка меток и форматирования для оси X
        mpLineChart.xAxis.setLabelCount(3, true) // Устанавливаем количество меток на оси X
        mpLineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return "${value.toInt()} день" // Форматируем значения в дни
            }
        }

        // Настройка меток и форматирования для оси Y
        mpLineChart.axisLeft.setLabelCount(5, true) // Устанавливаем количество меток на оси Y
        mpLineChart.axisLeft.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return "${value.toInt()} ккал." // Добавляем " КМ" к числу и возвращаем как строку
            }
        }

        mpLineChart.axisRight.isEnabled = false
        mpLineChart.legend.isEnabled = false
        mpLineChart.invalidate() // Обновляем график

        backButton=findViewById(R.id.backbutton)
        backButton.setOnClickListener {
            val backIntent = Intent(this,PersonalAccountActivity::class.java)
            startActivity(backIntent)
        }
    }
}