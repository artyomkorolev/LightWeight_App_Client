package com.example.lightweight.GalleryActivities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.example.lightweight.R
import com.example.lightweight.retrofit.GalleryApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.Calendar
import java.util.Locale

class AddPhotoProgressActivity : AppCompatActivity() {
    private lateinit var image : ImageView
    private lateinit var backbutton:Button
    private lateinit var addPhoto:ImageView
    private lateinit var saveButton: Button
    private lateinit var setMass:TextView
    private lateinit var setDate:TextView
    var picturePath: String? = null
    private lateinit var authtoken:String



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            val filePathColumn = MediaStore.Images.Media.DATA
            val cursor = contentResolver.query(selectedImage!!, arrayOf(filePathColumn), null, null, null)
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn)
            picturePath = cursor.getString(columnIndex)
            cursor.close()
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath))
            image.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo_progress)
        image = findViewById(R.id.addedImage)
        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        authtoken = sharedPreferences.getString("authToken", "") ?: ""
        authtoken = "Bearer $authtoken"

        image.visibility = View.GONE

        backbutton=findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val backIntent = Intent(this, GalleryActivity::class.java)
            startActivity(backIntent)
        }

        saveButton=findViewById(R.id.saveButton)


        addPhoto = findViewById(R.id.addphoto)
        addPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
        val selectedDate = Calendar.getInstance()

        val dateFormatForRequest = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
        var selectedDate1 = dateFormatForRequest.format(selectedDate.time)
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

                    val dateFormatForRequest = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
                    selectedDate1 = dateFormatForRequest.format(selectedDate.time)
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



        saveButton.setOnClickListener {
            if (picturePath != null) {
                val file = File(picturePath)
                val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))

                val weightString = setMass.text.toString()
                val weight = weightString.replace("кг", "").trim().toInt()



                val retrofit = Retrofit.Builder()
                    .baseUrl("https://light-weight.site:8080") // Replace with your actual base URL
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(GalleryApi::class.java)

                val call = service.addPhoto(authtoken, selectedDate1, weight, body)

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val backIntent = Intent(this@AddPhotoProgressActivity, GalleryActivity::class.java)
                            backIntent.putExtra("refresh", true)
                            startActivity(backIntent)

                        } else {

                            Toast.makeText(this@AddPhotoProgressActivity, "Error uploading photo ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // Handle failure
                        Toast.makeText(this@AddPhotoProgressActivity, "Failed to upload photo", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@AddPhotoProgressActivity, "Please select an image first", Toast.LENGTH_SHORT).show()
            }



        }
    }




}