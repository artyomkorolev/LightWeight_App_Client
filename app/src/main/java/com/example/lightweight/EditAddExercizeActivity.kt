package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class EditAddExercizeActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var saveButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add_exercize)

        backButton = findViewById(R.id.backbutton)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val backIntent= Intent(this,EditExercizeActivity::class.java)
            startActivity(backIntent)
            Toast.makeText(applicationContext,"Вы сохранили Упражнение", Toast.LENGTH_SHORT).show()
        }
        backButton.setOnClickListener {
            val backIntent= Intent(this,EditExercizeActivity::class.java)
            startActivity(backIntent)
        }
    }
}