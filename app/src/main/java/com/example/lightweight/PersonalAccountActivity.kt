package com.example.lightweight

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast

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
            val statIntent = Intent(this, StatFKActivity::class.java)
            startActivity(statIntent)

        }
        statFood.setOnClickListener {
           val statIntent = Intent(this, StatFoodActivity::class.java)
           startActivity(statIntent)

        }

        profileHeight = findViewById(R.id.profileHeightEdit)
        profileWidth  = findViewById(R.id.profileWidthEdit)


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
                val duration = numberPicker.value // Получаем выбранное значение
                profileHeight.text = "$duration см"

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
                val duration = numberPicker.value // Получаем выбранное значение
                profileWidth.text = "$duration кг"
                dialog.dismiss()
            }

            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(numberPicker)
            builder.show()
        }
    }
}