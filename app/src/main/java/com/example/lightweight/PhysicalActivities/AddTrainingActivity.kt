package com.example.lightweight.PhysicalActivities

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.ExercizeAdapter
import com.example.lightweight.Adapters.FoodItemAdapter
import com.example.lightweight.Adapters.TrainingAdapter
import com.example.lightweight.EatingActivities.AddEatingActivity
import com.example.lightweight.EatingActivities.MainActivity
import com.example.lightweight.Models.Eating
import com.example.lightweight.Models.Exercize
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.Models.GetEating
import com.example.lightweight.Models.GetTraining
import com.example.lightweight.Models.Training
import com.example.lightweight.R
import com.example.lightweight.ViewHolders.ExercizeViewHolder
import com.example.lightweight.retrofit.EatingApi
import com.example.lightweight.retrofit.ExerciseApi
import com.example.lightweight.retrofit.FoodItemApi
import com.example.lightweight.retrofit.WorkoutApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddTrainingActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var rvExercizeList:RecyclerView
    private lateinit var etTime:TextView
    private lateinit var etDuration: TextView
    private lateinit var saveButton: Button
    private lateinit var addExercizeButton: ImageView
    private lateinit var etSearchExercize: EditText
    private var exercises = ArrayList<Exercize>()
    private var exercizes =  mutableMapOf<UUID, Double>()
    private var startTime: String = ""
    private var endTime: String = ""
    private val formatDate = SimpleDateFormat("HH:mm", Locale("ru"))
    private val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private lateinit var authtoken:String
    private var isGuest: Boolean = false
    private var prod = mutableListOf<Pair<Exercize, Double>>()
    companion object {
        const val REQUEST_CODE_ADD_FOOD = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_training)
        addExercizeButton = findViewById(R.id.addExercize)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        if (authtoken.isEmpty()) {
            isGuest = true
            addExercizeButton.visibility =View.GONE
        } else {
            authtoken = "Bearer $authtoken"
            addExercizeButton.visibility =View.VISIBLE
        }

        backButton = findViewById(R.id.backbutton)
        rvExercizeList = findViewById(R.id.rvExercizeList)
        etTime = findViewById(R.id.etTime)
        etDuration= findViewById(R.id.etDuration)
        saveButton = findViewById(R.id.saveButton)

        etSearchExercize = findViewById(R.id.imputEditText)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getExercisesService = retrofit.create(ExerciseApi::class.java)
        val addTrainingApiService = retrofit.create(WorkoutApi::class.java)

        val selectedDateMillis = intent.getLongExtra("selectedDate1", 0)

        val selectedDate = Calendar.getInstance()
        selectedDate.timeInMillis = selectedDateMillis
        startTime = isoFormat.format(selectedDate.time)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)

        Log.d("SELECTEDDATA", formattedDate)

        addExercizeButton.setOnClickListener {
            val exercizeIntent = Intent(this, AddExerciseActivity::class.java)
            startActivityForResult(exercizeIntent, REQUEST_CODE_ADD_FOOD)
        }


        backButton.setOnClickListener {
            val backIntent=Intent(this, ActivityPhysical::class.java)
            startActivity(backIntent)
        }


        val exercizeAdapter = ExercizeAdapter(
           exercises,
            object : ExercizeAdapter.ExercizeActionListener{
                override fun OnClickItem(exercize: Exercize) {

                }
            }, object : ExercizeAdapter.OnItemClickListener{
                override fun onSaveClick(exercize: Exercize,holder: ExercizeViewHolder) {
                    val countValue = holder.getGrammValue()
                    if(countValue.isNullOrEmpty()){
                        val alertDialog = AlertDialog.Builder(this@AddTrainingActivity)
                        alertDialog.setTitle("Ошибка")
                        alertDialog.setMessage("Перед добавлением введите количество ${exercize.unit}")
                        alertDialog.setPositiveButton("ОК") { _, _ ->
                        }
                        alertDialog.show()
                    }else{
                    exercizes[UUID.fromString(exercize.id)] = countValue.toDouble()
                    prod.add(exercize to countValue.toDouble())
                }}

                override fun onDeleteClick(exercize: Exercize) {
                    exercizes.remove(UUID.fromString(exercize.id))
                    val iterator = prod.iterator()
                    while (iterator.hasNext()) {
                        val (item, _) = iterator.next()
                        if (item.id == exercize.id) {
                            iterator.remove()
                            break
                        }
                    }
                }

                override fun onGrammChange(exercize: Exercize, newCount: String) {

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

                    selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedDate.set(Calendar.MINUTE, minute)
                    val formatDate = SimpleDateFormat("HH:mm", Locale("ru"))
                    val formattedTime = formatDate.format(selectedDate.time)
                    etTime.text = formattedTime // Устанавливаем выбранное время в EditText
                    startTime = isoFormat.format(selectedDate.time)

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
                if (startTime.isNotBlank()) {

                        // Создаем Calendar и устанавливаем время начала
                        val endTimeCalendar = Calendar.getInstance()
                        endTimeCalendar.time = isoFormat.parse(startTime) ?: Date()

                        // Добавляем к времени начала продолжительность тренировки в минутах
                        endTimeCalendar.add(Calendar.MINUTE, duration)

                        // Форматируем endTime в ISO формате
                        val formattedEndTime = isoFormat.format(endTimeCalendar.time)

                        // Выводим endTime в лог и на экран
                        endTime = formattedEndTime
                        Log.d("Artiiii", endTime)
                    Log.d("Training", "Start Time: $startTime, End Time: $endTime")
                dialog.dismiss()
            }}

            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(numberPicker)
            builder.show()
        }
        saveButton.setOnClickListener {
            if (endTime.isNullOrEmpty()){
                Toast.makeText(applicationContext, "Вы не указали продолжительность тренировки", Toast.LENGTH_SHORT).show()
            }else{
            if (isGuest){
                saveGuestEating(Training(startTime,endTime,exercizes),prod)
            }else{

            val callTraining = addTrainingApiService.addTraining(authtoken,
                Training(startTime,endTime,exercizes)
            )
            callTraining.enqueue(object : Callback<Void>{
                override fun onResponse(
                    p0: Call<Void>,
                    p1: Response<Void>) {
                    if (p1.isSuccessful) {

                    } else {
                        Toast.makeText(applicationContext, "Ошибка: ${p1.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(p0: Call<Void>, p1: Throwable) {
                    Log.e("NetworkError", "Failed to execute request", p1)
                    Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()
                }

            })}





            val backIntent=Intent(this, ActivityPhysical::class.java)
            startActivity(backIntent)

        }}


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

        loadFoodItems(getExercisesService, exercizeAdapter)

    }
    private fun loadFoodItems(getFoodItemsService: ExerciseApi, foodItemAdapter: ExercizeAdapter) {
        val call = getFoodItemsService.getAllExercise(authtoken)
        call.enqueue(object : Callback<List<Exercize>> {
            override fun onResponse(
                call: Call<List<Exercize>>,
                response: Response<List<Exercize>>
            ) {
                if(response.code() == 200){
                    exercises.clear()
                    if (response.body()?.isNotEmpty() == true){
                        exercises.addAll(response.body()!!)
                        foodItemAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(p0: Call<List<Exercize>>, p1: Throwable) {
                Log.e("NetworkError", "Failed to execute request", p1)
                Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveGuestEating(eating: Training, foodItem: List<Pair<Exercize, Double>>) {

        val getEating = eating.toGetTraining(foodItem)

        // Сохраняем GetEating в SharedPreferences
        val sharedPreferences = getSharedPreferences("guestTrainings", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val existingEatingsJson = sharedPreferences.getString("trainings", "[]")
        val existingEatings: MutableList<GetTraining> = Gson().fromJson(existingEatingsJson, object : TypeToken<MutableList<GetTraining>>() {}.type)
        existingEatings.add(getEating)
        editor.putString("trainings", Gson().toJson(existingEatings))
        editor.apply()
        val backIntent=Intent(this, ActivityPhysical::class.java)
        startActivity(backIntent)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_FOOD && resultCode == RESULT_OK) {
            // Упражнение успешно добавлено, обновляем список упражнений
            val retrofit = Retrofit.Builder()
                .baseUrl("https://light-weight.site:8080")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val getExercisesService = retrofit.create(ExerciseApi::class.java)
            val trainingAdapter = rvExercizeList.adapter as ExercizeAdapter
            loadFoodItems(getExercisesService, trainingAdapter)
        }
    }

}
