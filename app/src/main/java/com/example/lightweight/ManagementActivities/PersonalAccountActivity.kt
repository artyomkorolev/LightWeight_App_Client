package com.example.lightweight.ManagementActivities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
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
    private var isGuest: Boolean = false
    private lateinit var profileName:TextView
    private lateinit var nameFood:TextView
    private lateinit var textHei:TextView
    private lateinit var textWid:TextView
    private lateinit var editPass:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_account)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        management = findViewById(R.id.management)
        statFK = findViewById(R.id.statFK)
        statFood = findViewById(R.id.statFood)
        profileHeight = findViewById(R.id.profileHeightEdit)
        profileWidth  = findViewById(R.id.profileWidthEdit)
        buttonFk=findViewById(R.id.buttonFK)
        buttonGallery = findViewById(R.id.buttonGallery)
        buttonFood = findViewById(R.id.buttonFood)
        tvLogin = findViewById(R.id.tvLogin)
        buttonAuth =findViewById(R.id.authActivity)
        buttonExit=findViewById(R.id.exitAuth)
        profileName =findViewById(R.id.profileName)
        textHei = findViewById(R.id.profileHeight)
        textWid =findViewById(R.id.profileWidth)
        nameFood = findViewById(R.id.nameFood)
        editPass =findViewById(R.id.editpass)

        if (!authtoken.isNullOrEmpty()){
            buttonAuth.visibility = View.GONE
            buttonExit.visibility = View.VISIBLE
        }
        if (authtoken.isEmpty()) {
            isGuest = true
            management.visibility=View.GONE
            statFK.visibility=View.GONE
            statFood.visibility=View.GONE
            profileHeight.visibility=View.GONE
            profileWidth.visibility=View.GONE
            tvLogin.visibility=View.GONE
            profileName.text ="Гость"
            textHei.visibility = View.GONE
            textWid.visibility =View.GONE
            nameFood.visibility = View.GONE
            editPass.visibility=View.GONE

        } else {
            authtoken = "Bearer $authtoken"
            management.visibility=View.VISIBLE
            statFK.visibility=View.VISIBLE
            statFood.visibility=View.VISIBLE
            profileHeight.visibility=View.VISIBLE
            profileWidth.visibility=View.VISIBLE
            tvLogin.visibility=View.VISIBLE
            textHei.visibility = View.VISIBLE
            textWid.visibility =View.VISIBLE
            nameFood.visibility = View.VISIBLE
            editPass.visibility=View.VISIBLE
        }

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
                        profileName.text = user.login
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

        editPass.setOnClickListener {
            val editText = EditText(this).apply {
                hint = "Введите новый пароль"
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Изменение пароля")
            builder.setMessage("Авторизоваться придется заново:")

            builder.setPositiveButton("OK") { dialog, _ ->
                val newPass = editText.text.toString()
                updatePassword(newPass)
                val managementIntent = Intent(this, LoginActivity::class.java)
                startActivity(managementIntent)
                dialog.dismiss()

            }

            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(editText)
            builder.show()
        }

        tvLogin.setOnClickListener {
            val editText = EditText(this).apply {
                hint = "Введите новый логин"
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Изменение логина")
            builder.setMessage("Авторизоваться придется заново:")

            builder.setPositiveButton("OK") { dialog, _ ->
                val newLogin = editText.text.toString()
                val updatedUser = User(
                    height = profileHeight.text.toString().removeSuffix(" см").toInt(),
                    weight = profileWidth.text.toString().removeSuffix(" кг").toInt(),
                    login = newLogin
                )
                updateUser(updatedUser) { success ->
                    if (success) {
                        tvLogin.text = newLogin
                        clearAuthToken()
                        val managementIntent = Intent(this, LoginActivity::class.java)
                        startActivity(managementIntent)
                        dialog.dismiss()
                    }
                }

            }

            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(editText)
            builder.show()
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

                val updatedUser = User(height = height, weight = profileWidth.text.toString().removeSuffix(" кг").toInt(), login = loginP)
                updateUser(updatedUser) { success ->
                    if (success) {
                        profileHeight.text = "$height см"
                    }
                }

                clearAuthToken()
                val managementIntent = Intent(this, LoginActivity::class.java)
                startActivity(managementIntent)
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

                val updatedUser = User(height = profileHeight.text.toString().removeSuffix(" см").toInt(), weight = weight, login = loginP)
                updateUser(updatedUser) { success ->
                    if (success) {
                        profileWidth.text = "$weight кг"
                    }
                }
                dialog.dismiss()
            }

            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(numberPicker)
            builder.show()
        }


    }
    private fun updateUser(user: User, callback: (Boolean) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val userApiService = retrofit.create(PersonalAccApi::class.java)

        val call = userApiService.postUpdateinfo(authtoken, user)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {

                    callback(true)
                } else {
                    val errorMessage = response.errorBody()?.string()
                    if (errorMessage != null && errorMessage.contains("login already taken", ignoreCase = true)) {
                        Toast.makeText(applicationContext, "Ошибка: Логин уже занят. Пожалуйста, выберите другой логин.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "Ошибка: Логин уже занят. Пожалуйста, выберите другой логин.", Toast.LENGTH_LONG).show()
                    }
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("NetworkError", "Failed to execute request", t)
                Toast.makeText(applicationContext, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
                callback(false)
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
    private fun updatePassword(password:String){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://light-weight.site:8080")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val userApiService = retrofit.create(PersonalAccApi::class.java)

        val call = userApiService.postUpdatePassword(authtoken, password)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("NetworkError", "Failed to execute request", t)
                Toast.makeText(applicationContext, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}