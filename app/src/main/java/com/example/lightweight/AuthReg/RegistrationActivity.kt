package com.example.lightweight.AuthReg

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lightweight.EatingActivities.MainActivity
import com.example.lightweight.R
import com.example.lightweight.retrofit.PersonalAccApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrationActivity : AppCompatActivity() {
    private lateinit var fieldLogin: EditText
    private lateinit var fieldPassword: EditText
    private lateinit var apply: TextView
    private lateinit var buttonLogin:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        fieldLogin = findViewById(R.id.login)
        fieldPassword = findViewById(R.id.password)
        apply = findViewById(R.id.apply)


        buttonLogin =findViewById(R.id.registration)


        buttonLogin.setOnClickListener {
            val intent = Intent(this@RegistrationActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        apply.setOnClickListener {
            val login = fieldLogin.text.toString()
            val password = fieldPassword.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                emptyFields()
            } else {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://light-weight.site:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val authApi = retrofit.create(PersonalAccApi::class.java)

                val call =authApi.register(UserAuth(login,password))
                call.enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(p0: Call<AuthResponse>, response: Response<AuthResponse>) {
                        if (response.isSuccessful) {
                            val authResponse = response.body()
                            if (authResponse != null) {
                                val intent = Intent(this@RegistrationActivity,LoginActivity::class.java)
                                startActivity(intent)

                            } else {
                                Toast.makeText(applicationContext, "Authentication failed: response body is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            noLoginOrPAss()
                        }
                    }

                    override fun onFailure(p0: Call<AuthResponse>, p1: Throwable) {
                        Toast.makeText(applicationContext, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }

    }
    private fun emptyFields() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Неккоректные данные")
        alertDialog.setMessage("Вы не ввели логин или пароль")

        alertDialog.setPositiveButton("ОК") { _, _ ->



        }


        alertDialog.show()
    }
    private fun noLoginOrPAss() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Неккоректные данные")
        alertDialog.setMessage("Пользователь с таким логином уже существует")

        alertDialog.setPositiveButton("ОК") { _, _ ->



        }


        alertDialog.show()
    }
}