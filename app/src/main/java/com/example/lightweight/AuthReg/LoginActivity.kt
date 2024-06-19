package com.example.lightweight.AuthReg

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

class LoginActivity : AppCompatActivity() {
    private lateinit var fieldLogin:EditText
    private lateinit var fieldPassword:EditText
    private lateinit var apply:TextView
    private lateinit var viewModel:LoginViewModel
    private lateinit var  authtoken:String
    private lateinit var welcome:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fieldLogin = findViewById(R.id.login)
        fieldPassword = findViewById(R.id.password)
        apply = findViewById(R.id.apply)
        welcome =findViewById(R.id.welcome)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        
        apply.setOnClickListener {
            val login = fieldLogin.text.toString()
            val password = fieldPassword.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                // Handle the case where the user did not enter both a login and a password
                Toast.makeText(this, "Please enter both a login and a password", Toast.LENGTH_SHORT).show()
            } else {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://212.113.121.36:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val authApi = retrofit.create(PersonalAccApi::class.java)

                val call =authApi.authenticate(UserAuth(login,password))
                call.enqueue(object :Callback<AuthResponse>{
                    override fun onResponse(p0: Call<AuthResponse>, response: Response<AuthResponse>) {
                        if (response.isSuccessful) {
                            val authResponse = response.body()
                            if (authResponse != null) {
                                val authtoken = authResponse.access_token
                                val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("authToken", authtoken)
                                editor.apply()

                                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                                startActivity(intent)

                            } else {
                                Toast.makeText(applicationContext, "Authentication failed: response body is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Authentication failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(p0: Call<AuthResponse>, p1: Throwable) {
                        Log.e("NetworkError", "Failed to execute request", p1)
                        Toast.makeText(applicationContext, "Network Error: ${p1.message}", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }



    }
}