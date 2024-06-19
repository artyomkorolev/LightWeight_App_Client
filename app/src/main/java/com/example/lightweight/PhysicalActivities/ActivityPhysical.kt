package com.example.lightweight.PhysicalActivities

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.TrainingAdapter
import com.example.lightweight.EatingActivities.MainActivity
import com.example.lightweight.GalleryActivities.GalleryActivity
import com.example.lightweight.Models.Training
import com.example.lightweight.ManagementActivities.PersonalAccountActivity
import com.example.lightweight.Models.GetEating
import com.example.lightweight.Models.GetTraining
import com.example.lightweight.R
import com.example.lightweight.retrofit.EatingApi
import com.example.lightweight.retrofit.WorkoutApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.Locale

class ActivityPhysical : AppCompatActivity() {
    private lateinit var addTrainingButton: ImageView
    //BOTTOMBAR
    private lateinit var buttonFood: Button
    private lateinit var buttonGallery: Button
    private lateinit var buttonLK: Button

    private lateinit var tvdate:TextView
    private lateinit var rvTrainingList:RecyclerView
    private var trainings = ArrayList<GetTraining>()
    private lateinit var authtoken:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physical)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"


        val selectedDate = Calendar.getInstance()
        var savedDate = SimpleDateFormat("yyyy-MM-dd", Locale("ru")).format(selectedDate.time)

        rvTrainingList = findViewById(R.id.rvPhysicalList)
        tvdate = findViewById(R.id.tvDate)
        addTrainingButton = findViewById(R.id.addTraining)
        buttonFood=findViewById(R.id.buttonFood)
        buttonGallery = findViewById(R.id.buttonGallery)
        buttonLK = findViewById(R.id.buttonLK)

        buttonFood.setOnClickListener{
            val fkIntent = Intent(this, MainActivity::class.java)
            startActivity(fkIntent)
        }
        buttonGallery.setOnClickListener{
            val galIntent = Intent(this, GalleryActivity::class.java)
            startActivity(galIntent)
        }
        buttonLK.setOnClickListener{
            val galIntent = Intent(this, PersonalAccountActivity::class.java)
            startActivity(galIntent)
        }


        val trainingAdapter = TrainingAdapter(
            trainings,
            object : TrainingAdapter.TrainingActionListener{
                override fun OnClickItem(training: GetTraining) {
                    val checkIntent = Intent(this@ActivityPhysical, CheckTrainingActivity::class.java)
                    checkIntent.putExtra("exesises",ArrayList(training.exercises))

                    checkIntent.putExtra("idTraining",training.id)
                    startActivity(checkIntent)
                }
            }

        )
        rvTrainingList.adapter = trainingAdapter

        val dateFormat = SimpleDateFormat("EEEE, d MMM", Locale("ru"),)

        tvdate.text = dateFormat.format(Calendar.getInstance().time)
        tvdate.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->


                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("EEEE, d MMM", Locale("ru"),)
                    val formattedDate = dateFormat.format(selectedDate.time)
                    val tvDate = findViewById<TextView>(R.id.tvDate)
                    tvDate.text = formattedDate
                    val dateFormat1 = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))

                    val formatDate  =dateFormat1.format(selectedDate.time)
                    savedDate = formatDate
                    getTrainings(trainingAdapter,savedDate,authtoken)
                    // Устанавливаем отформатированную дату в TextView

                },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
        addTrainingButton.setOnClickListener{
            val addIntent = Intent(this, AddTrainingActivity::class.java)
            addIntent.putExtra("selectedDate1", selectedDate.timeInMillis)
            startActivity(addIntent)
        }

        getTrainings(trainingAdapter,savedDate,authtoken)
    }
    private fun getTrainings(trainingAdapter: TrainingAdapter,savedDate:String,authToken:String){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://212.113.121.36:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getFoodItemsService = retrofit.create(WorkoutApi::class.java)

        val call = getFoodItemsService.getWorkoutByDate(authToken,savedDate)
        call.enqueue(object :Callback<List<GetTraining>>{
            override fun onResponse(
                p0: Call<List<GetTraining>>,
                response: Response<List<GetTraining>>
            ) {
                if (response.isSuccessful) {
                    trainings.clear()
                    trainings.addAll(response.body()!!)
                    trainingAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(applicationContext, "Network Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(p0: Call<List<GetTraining>>, p1: Throwable) {
                Log.e("NetworkError", "Failed to execute request", p1)
                Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}