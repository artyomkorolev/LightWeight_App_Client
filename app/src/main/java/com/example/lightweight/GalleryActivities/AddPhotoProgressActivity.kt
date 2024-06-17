package com.example.lightweight.GalleryActivities

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.lightweight.Models.Photo
import com.example.lightweight.R
import com.example.lightweight.retrofit.GalleryApi
import com.example.lightweight.retrofit.UploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
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
    private var selectedImageUri: Uri? = null


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            selectImageFromGallery()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    val retrofit = Retrofit.Builder()
        .baseUrl("http://212.113.121.36:8080")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val photoApiService = retrofit.create(GalleryApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo_progress)
        image = findViewById(R.id.addedImage)
        Glide.with(applicationContext).load(R.drawable.fat).into(image)
        image.visibility = View.GONE

        backbutton=findViewById(R.id.backbutton)
        backbutton.setOnClickListener {
            val backIntent = Intent(this, GalleryActivity::class.java)
            startActivity(backIntent)
        }

        saveButton=findViewById(R.id.saveButton)


        addPhoto = findViewById(R.id.addphoto)
        addPhoto.setOnClickListener {
            checkPermissionAndSelectImage()
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

        saveButton.setOnClickListener {
            if (selectedImageUri != null) {
                uploadImage(selectedImageUri!!)
                val saveIntent = Intent(this, GalleryActivity::class.java)
                startActivity(saveIntent)
            } else {
                Toast.makeText(applicationContext, "No image selected", Toast.LENGTH_SHORT).show()
            }
//
        }
    }
    private fun checkPermissionAndSelectImage() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                selectImageFromGallery()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                Toast.makeText(this, "Permission required to access gallery", Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            image.setImageURI(it)
        }
    }
    private fun selectImageFromGallery() {
        getContent.launch("image/*")
        image.visibility = View.VISIBLE
    }

    private fun uploadImage(uri: Uri) {
        Log.d("UploadImage", "Starting upload process for URI: $uri")
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), input.readBytes())

            val weightText = setMass.text.toString().replace(" кг", "")
            val weight = weightText.toIntOrNull() ?: 0

            val body = MultipartBody.Part.createFormData("file", "image.jpg", requestBody)
            val weightRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), weight.toString())

            val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
            val dateTimeText = setDate.text.toString()
            val date = dateFormat.parse(dateTimeText)

            val call = photoApiService.addPhoto(
                "Bearer your_token_here", // Replace with actual token
                body,
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date),
                weight
            )

            call.enqueue(object : Callback<Photo> {
                override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Upload successful", Toast.LENGTH_SHORT).show()
                        val saveIntent = Intent(applicationContext, GalleryActivity::class.java)
                        startActivity(saveIntent)
                    } else {
                        Toast.makeText(applicationContext, "Upload failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Photo>, t: Throwable) {
                    Log.e("UploadImage", "Upload failed: ${t.message}", t)
                    Toast.makeText(applicationContext, "Upload failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })


        }
    }}