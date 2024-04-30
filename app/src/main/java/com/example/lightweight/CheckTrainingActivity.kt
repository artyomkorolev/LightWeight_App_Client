package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class CheckTrainingActivity : AppCompatActivity() {
    private lateinit var backbutton:Button
    private lateinit var deleteButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_training)
        backbutton=findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val backIntent = Intent(this,ActivityPhysical::class.java)
            startActivity(backIntent)
        }

        deleteButton=findViewById(R.id.buttonDelete)
        deleteButton.setOnClickListener {

            val saveIntent = Intent(this,ActivityPhysical::class.java)
            startActivity(saveIntent)
            Toast.makeText(applicationContext,"Тренировка удалена", Toast.LENGTH_SHORT).show()

        }
    }
}