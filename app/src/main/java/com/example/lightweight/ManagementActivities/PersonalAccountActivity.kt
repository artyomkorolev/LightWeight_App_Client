package com.example.lightweight.ManagementActivities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.example.lightweight.AuthReg.LoginActivity
import com.example.lightweight.EatingActivities.MainActivity
import com.example.lightweight.GalleryActivities.GalleryActivity
import com.example.lightweight.PhysicalActivities.ActivityPhysical
import com.example.lightweight.R
import com.example.lightweight.ManagementActivities.GraphActivities.StatFKActivity
import com.example.lightweight.ManagementActivities.GraphActivities.StatFoodActivity
import com.example.lightweight.Models.User
import com.example.lightweight.retrofit.ExerciseApi
import com.example.lightweight.retrofit.PersonalAccApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonalAccountActivity : AppCompatActivity() {
    private lateinit var buttonFk: Button
    private lateinit var buttonGallery: Button
    private lateinit var buttonFood: Button
    private lateinit var management:TextView
    private lateinit var profileWidth:TextView
    private lateinit var profileHeight:TextView
    private lateinit var statFK: TextView
    private lateinit var statFood:TextView
    private lateinit var tvLogin:TextView
    private var loginP= ""
    private lateinit var buttonAuth:TextView
    private lateinit var authtoken:String
    private lateinit var buttonExit:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_account)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""

        profileHeight = findViewById(R.id.profileHeightEdit)
        profileWidth  = findViewById(R.id.profileWidthEdit)
        buttonFk=findViewById(R.id.buttonFK)
        buttonGallery = findViewById(R.id.buttonGallery)
        buttonFood = findViewById(R.id.buttonFood)
        tvLogin = findViewById(R.id.tvLogin)
        buttonAuth =findViewById(R.id.authActivity)
        buttonExit=findViewById(R.id.exitAuth)

        if (!authtoken.isNullOrEmpty()){
            buttonAuth.visibility = View.GONE
            buttonExit.visibility = View.VISIBLE
        }
        authtoken = "Bearer $authtoken"

        buttonExit.setOnClickListener {
            clearAuthToken()
            val managementIntent = Intent(this, LoginActivity::class.java)
            startActivity(managementIntent)
        }


        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val userApiService = retrofit.create(PersonalAccApi::class.java)

        val call = userApiService.getUserInfo(authtoken)
        call.enqueue(object :Callback<User>{
            override fun onResponse(
                call: Call<User>,
                response: Response<User>) {
                val point =0
                if(response.isSuccessful){
                    val user = response.body()
                    if (user != null) {
                        profileHeight.text = user.height.toString() + " см"
                        profileWidth.text = user.weight.toString()+ " кг"
                        tvLogin.text = user.login
                        loginP = user.login
                    } else {
                        Log.e("ResponseError", "User object is null")
                    }
                } else {
                    Log.e("ResponseError", "Response code: ${response.code()}")
                    Log.e("ResponseError", "Response message: ${response.message()}")
                    Log.e("ResponseError", "Response error body: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(p0: Call<User>, p1: Throwable) {
                Log.e("NetworkError", "Failed to execute request", p1)
                Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()
            }

        })
        buttonFk.setOnClickListener{
            val fkIntent = Intent(this, ActivityPhysical::class.java)
            startActivity(fkIntent)
        }
        buttonGallery.setOnClickListener{
            val galIntent = Intent(this, GalleryActivity::class.java)
            startActivity(galIntent)
        }
        buttonFood.setOnClickListener{
            val galIntent = Intent(this, MainActivity::class.java)
            startActivity(galIntent)
        }
        buttonAuth.setOnClickListener{
            val galIntent = Intent(this,LoginActivity::class.java)
            startActivity(galIntent)
        }

        management = findViewById(R.id.management)
        statFK = findViewById(R.id.statFK)
        statFood = findViewById(R.id.statFood)

        management.setOnClickListener {
            val managementIntent = Intent(this, ManagementActivity::class.java)
            startActivity(managementIntent)
        }
        statFK.setOnClickListener {
            val statIntent = Intent(this, StatFKActivity::class.java)
            startActivity(statIntent)

        }
        statFood.setOnClickListener {
           val statIntent = Intent(this, StatFoodActivity::class.java)
           startActivity(statIntent)

        }




        profileHeight.setOnClickListener {
            val numberPicker = NumberPicker(this).apply {
                minValue = 100// Минимальное значение
                maxValue = 260 // Максимальное значение
                value = 180 // Значение по умолчанию
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Укажите ваш рост")
            builder.setMessage("В СМ:")

            builder.setPositiveButton("OK") { dialog, _ ->
                val height = numberPicker.value // Получаем выбранное значение
                profileHeight.text = "$height см"
                val updatedUser = User(height = height, weight = profileWidth.text.toString().removeSuffix(" кг").toInt(), login = loginP)
                updateUser(updatedUser)
                dialog.dismiss()
            }

            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(numberPicker)
            builder.show()
        }

        profileWidth.setOnClickListener {
            val numberPicker = NumberPicker(this).apply {
                minValue = 40// Минимальное значение
                maxValue = 200 // Максимальное значение
                value = 60 // Значение по умолчанию
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Сколько вы весите")
            builder.setMessage("В КГ:")

            builder.setPositiveButton("OK") { dialog, _ ->
                val weight = numberPicker.value // Получаем выбранное значение
                profileWidth.text = "$weight кг"
                val updatedUser = User(height = profileHeight.text.toString().removeSuffix(" см").toInt(), weight = weight, login = loginP)
                updateUser(updatedUser)
                dialog.dismiss()
            }

            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(numberPicker)
            builder.show()
        }


    }
    private fun updateUser(user: User) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val userApiService = retrofit.create(PersonalAccApi::class.java)

        val call = userApiService.postUpdateinfo(authtoken, user)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {

                } else {
                    Log.e("ResponseError", "Response code: ${response.code()}")
                    Log.e("ResponseError", "Response message: ${response.message()}")
                    Log.e("ResponseError", "Response error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("NetworkError", "Failed to execute request", t)
                Toast.makeText(applicationContext, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun clearAuthToken() {
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("authToken")
        editor.apply()
        buttonAuth.visibility = View.VISIBLE
        buttonExit.visibility = View.GONE

    }
}