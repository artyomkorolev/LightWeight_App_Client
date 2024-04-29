package com.example.lightweight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class PersonalAccountActivity : AppCompatActivity() {
    private lateinit var buttonFk: Button
    private lateinit var buttonGallery: Button
    private lateinit var buttonFood: Button
    private lateinit var management:TextView
    private lateinit var profileWidth:TextView
    private lateinit var profileHeight:TextView
    private lateinit var statFK: TextView
    private lateinit var statFood:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_account)

        buttonFk=findViewById(R.id.buttonFK)
        buttonGallery = findViewById(R.id.buttonGallery)
        buttonFood = findViewById(R.id.buttonFood)
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

        management = findViewById(R.id.management)
        statFK = findViewById(R.id.statFK)
        statFood = findViewById(R.id.statFood)

        management.setOnClickListener {
            val managementIntent = Intent(this, ManagementActivity::class.java)
            startActivity(managementIntent)
        }
        statFK.setOnClickListener {
//            val statIntent = Intent(this, StatFKActivity::class.java)
//            startActivity(statIntent)
            Toast.makeText(applicationContext,"Пока не готово", Toast.LENGTH_SHORT).show()
        }
        statFood.setOnClickListener {
//            val statIntent = Intent(this, StatFoodActivity::class.java)
//            startActivity(statIntent)
            Toast.makeText(applicationContext,"Пока не готово", Toast.LENGTH_SHORT).show()
        }
    }
}