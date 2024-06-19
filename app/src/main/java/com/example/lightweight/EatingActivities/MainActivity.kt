package com.example.lightweight.EatingActivities

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.PhysicalActivities.ActivityPhysical
import com.example.lightweight.Adapters.EatingAdapter
import com.example.lightweight.GalleryActivities.GalleryActivity
import com.example.lightweight.Models.Eating
import com.example.lightweight.ManagementActivities.PersonalAccountActivity
import com.example.lightweight.Models.FoodItem
import com.example.lightweight.Models.GetEating
import com.example.lightweight.Models.Products
import com.example.lightweight.R
import com.example.lightweight.retrofit.EatingApi
import com.example.lightweight.retrofit.FoodItemApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var rvFoodList: RecyclerView
    private lateinit var tvDate: TextView
    private lateinit var currentCalories:TextView
    private lateinit var totalCalories: TextView
    private lateinit var addEatingButton: ImageView
    //BOTTOMBAR
    private lateinit var buttonFk: Button
    private lateinit var buttonGallery:Button
    private lateinit var buttonLK: Button
    private var eatings = ArrayList<GetEating>()
    private lateinit var authtoken: String


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LightWeight)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvFoodList = findViewById(R.id.rvFoodList)
        tvDate = findViewById(R.id.tvDate)
        currentCalories =findViewById(R.id.curCal)
        totalCalories = findViewById(R.id.totalCal)
        addEatingButton = findViewById(R.id.addEating)
        buttonFk=findViewById(R.id.buttonFK)
        buttonGallery = findViewById(R.id.buttonGallery)
        buttonLK = findViewById(R.id.buttonLK)
        val selectedDate = Calendar.getInstance()
        var savedDate = SimpleDateFormat("yyyy-MM-dd", Locale("ru")).format(selectedDate.time)


        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"

        buttonFk.setOnClickListener{
            val fkIntent = Intent(this, ActivityPhysical::class.java)
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


        val eatingAdapter = EatingAdapter(
           eatings,
            object : EatingAdapter.EatingActionListener{
                override fun OnClickItem(eating: GetEating) {
                    val checkIntent = Intent(this@MainActivity, CheckEatingActivity::class.java)
                    checkIntent.putExtra("products", ArrayList(eating.products) )
                    checkIntent.putExtra("idEating",eating.id)
                    startActivity(checkIntent)
                }
            },
            currentCalories

        )
        rvFoodList.adapter = eatingAdapter
        eatingAdapter.updateCalories()
        addEatingButton.setOnClickListener{


           val addIntent = Intent(this, AddEatingActivity::class.java)
            addIntent.putExtra("selectedDate", selectedDate.timeInMillis)
           startActivity(addIntent)
        }

        getEatings(eatingAdapter,savedDate,authtoken)

        //Data picker
    val dateFormat = SimpleDateFormat("EEEE, d MMM", Locale("ru"),)

    tvDate.text = dateFormat.format(Calendar.getInstance().time)
    tvDate.setOnClickListener {
        val getDate =Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->


                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, monthOfYear)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("EEEE, d MMM", Locale("ru"),)
                val formattedDate = dateFormat.format(selectedDate.time)

                val tvDate = findViewById<TextView>(R.id.tvDate)
                tvDate.text = formattedDate // Устанавливаем отформатированную дату в TextView
                val dateFormat1 = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
                val formatDate  =dateFormat1.format(selectedDate.time)
                 savedDate = formatDate
                getEatings(eatingAdapter,savedDate,authtoken)

            },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }




    }
    private fun getEatings(eatingAdapter: EatingAdapter,savedDate:String,authToken:String){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val getFoodItemsService = retrofit.create(EatingApi::class.java)

        val call = getFoodItemsService.getEatingByDate(authToken,savedDate)
        call.enqueue(object : Callback<List<GetEating>> {
            override fun onResponse(
                call: Call<List<GetEating>>,
                response: Response<List<GetEating>>
            ) {
                if (response.isSuccessful) {
                    eatings.clear()
                    eatings.addAll(response.body()!!)
                    eatingAdapter.notifyDataSetChanged()
                    eatingAdapter.updateCalories()

                }else{
                    Toast.makeText(applicationContext, "Network Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(p0: Call<List<GetEating>>, p1: Throwable) {
                Log.e("NetworkError", "Failed to execute request", p1)
                Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }
}