package com.example.lightweight

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import java.util.Calendar
import java.util.Locale

class AddPhotoProgressActivity : AppCompatActivity() {
    private lateinit var image : ImageView
    private lateinit var backbutton:Button
    private lateinit var addPhoto:ImageView
    private lateinit var saveButton: Button
    private lateinit var setMass:TextView
    private lateinit var setDate:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo_progress)
        image = findViewById(R.id.addedImage)
        Glide.with(applicationContext).load(R.drawable.fat).into(image)
        image.visibility = View.GONE

        backbutton=findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val backIntent = Intent(this,GalleryActivity::class.java)
            startActivity(backIntent)
        }

        saveButton=findViewById(R.id.saveButton)
        saveButton.setOnClickListener {

            val saveIntent = Intent(this,GalleryActivity::class.java)
            startActivity(saveIntent)
            Toast.makeText(applicationContext,"Прогресс сохранен",Toast.LENGTH_SHORT).show()

        }

        addPhoto = findViewById(R.id.addphoto)
        addPhoto.setOnClickListener {
            image.visibility=View.VISIBLE
        }

        setDate = findViewById(R.id.etProteins)
        setMass = findViewById(R.id.setMass)
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"),)

        setDate.text = dateFormat.format(Calendar.getInstance().time)
        setDate.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->

                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, monthOfYear)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"),)
                    val formattedDate = dateFormat.format(selectedDate.time)

                    setDate = findViewById(R.id.etProteins)
                    setDate.text = formattedDate // Устанавливаем отформатированную дату в TextView

                },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        setMass.setOnClickListener {
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
                setMass.text = "$duration кг"
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