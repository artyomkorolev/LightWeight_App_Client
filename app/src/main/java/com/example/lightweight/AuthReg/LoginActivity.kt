package com.example.lightweight.AuthReg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.lightweight.R
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var fieldLogin:EditText
    private lateinit var fieldPassword:EditText
    private lateinit var apply:TextView
    private lateinit var viewModel:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fieldLogin = findViewById(R.id.login)
        fieldPassword = findViewById(R.id.password)
        apply = findViewById(R.id.apply)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        
        apply.setOnClickListener {


        }



    }
}