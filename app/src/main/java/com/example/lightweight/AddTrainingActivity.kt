package com.example.lightweight

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class AddTrainingActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var rvExercizeList:RecyclerView
    private lateinit var etTime:TextView
    private lateinit var etDuration: TextView
    private lateinit var saveButton: Button
    private lateinit var addExercizeButton: ImageView
    private lateinit var etSearchExercize: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_training)

        backButton = findViewById(R.id.backbutton)
        rvExercizeList = findViewById(R.id.rvExercizeList)
        etTime = findViewById(R.id.etTime)
        etDuration= findViewById(R.id.etDuration)
        saveButton = findViewById(R.id.saveButton)
        addExercizeButton = findViewById(R.id.addExercize)
        etSearchExercize = findViewById(R.id.imputEditText)

        addExercizeButton.setOnClickListener {
            val exercizeIntent = Intent(this, AddExerciseActivity::class.java)
            startActivity(exercizeIntent)
        }


        backButton.setOnClickListener {
            val backIntent=Intent(this,ActivityPhysical::class.java)
            startActivity(backIntent)
        }
        saveButton.setOnClickListener {
            val backIntent=Intent(this,ActivityPhysical::class.java)
            startActivity(backIntent)
            Toast.makeText(applicationContext,"Тренировка добавлена", Toast.LENGTH_SHORT).show()
        }

        val exercizeAdapter = ExercizeAdapter(
            listOf(
                Exercize("Бег","КМ"),
                Exercize("Подтягивания","шт"),
                Exercize("Жим лежа","шт")

            ),
            object : ExercizeAdapter.ExercizeActionListener{
                override fun OnClickItem(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Вы нажали на упражнение",Toast.LENGTH_SHORT).show()
                }
            }, object :ExercizeAdapter.OnItemClickListener{
                override fun onSaveClick(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Сохранить",Toast.LENGTH_SHORT).show()
                }

                override fun onDeleteClick(exercize: Exercize) {
                    Toast.makeText(applicationContext,"Удалить",Toast.LENGTH_SHORT).show()
                }

                override fun onGrammChange(exercize: Exercize, newCount: String) {
                    Toast.makeText(applicationContext,"Изменить",Toast.LENGTH_SHORT).show()
                }

            }
        )
        rvExercizeList.adapter=exercizeAdapter
        val formatDate = SimpleDateFormat("HH:mm", Locale("ru"))
        etTime.text =formatDate.format(Calendar.getInstance().time)
        etTime.setOnClickListener {
            val getDate = Calendar.getInstance()
            val timePicker = TimePickerDialog(
                this, // Убедитесь, что используете правильный контекст
                { _, hourOfDay, minute ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedDate.set(Calendar.MINUTE, minute)
                    val formatDate = SimpleDateFormat("HH:mm", Locale("ru"))
                    val formattedTime = formatDate.format(selectedDate.time)
                    etTime.text = formattedTime // Устанавливаем выбранное время в EditText
                },
                getDate.get(Calendar.HOUR_OF_DAY), // Используйте HOUR_OF_DAY для 24-часового формата
                getDate.get(Calendar.MINUTE),
                true // Установите 'true' для 24-часового формата времени
            )
            timePicker.show()
        }

        val formatter = NumberPicker.Formatter { value ->
            val temp = value * 5
            "" + temp
        }


        etDuration.setOnClickListener {
            val numberPicker = NumberPicker(this).apply {
                minValue = 1 // Минимальное значение
                maxValue = 120 // Максимальное значение
                value = 6 // Значение по умолчанию
            }
            numberPicker.setFormatter(formatter)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Выберите длительность тренировки")
            builder.setMessage("Длительность в минутах:")

            builder.setPositiveButton("OK") { dialog, _ ->
                val duration = numberPicker.value*5 // Получаем выбранное значение
                etDuration.text = duration.toString()
                dialog.dismiss()
            }

            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(numberPicker)
            builder.show()
        }


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

    }
}