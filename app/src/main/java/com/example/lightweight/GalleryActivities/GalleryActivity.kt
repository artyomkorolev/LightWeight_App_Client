package com.example.lightweight.GalleryActivities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightweight.Adapters.PhotoAdapter
import com.example.lightweight.EatingActivities.MainActivity
import com.example.lightweight.Models.Photo
import com.example.lightweight.ManagementActivities.PersonalAccountActivity
import com.example.lightweight.PhysicalActivities.ActivityPhysical
import com.example.lightweight.R
import com.example.lightweight.retrofit.GalleryApi
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.util.Locale


class GalleryActivity : AppCompatActivity() {
    private lateinit var profileName:TextView
    private lateinit var addPhoto: ImageView
    private lateinit var rvPhotos:RecyclerView
    //BOTTOMBAR
    private lateinit var buttonFk: Button
    private lateinit var buttonFood: Button
    private lateinit var buttonLK: Button
    private val photos = ArrayList<Photo>()
    val photoAdapter = PhotoAdapter(
        photos,
        object : PhotoAdapter.OnItemClickListener{
            override fun onDeleteClick(photo: Photo) {
               deletePhoto(photo)

            }
        }

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        buttonFk=findViewById(R.id.buttonFK)
        buttonFood=findViewById(R.id.buttonFood)
        buttonLK = findViewById(R.id.buttonLK)
        addPhoto = findViewById(R.id.addphoto)
        rvPhotos = findViewById(R.id.rvPhotos)


        buttonFk.setOnClickListener{
            val fkIntent = Intent(this, ActivityPhysical::class.java)
            startActivity(fkIntent)
        }
        buttonFood.setOnClickListener{
            val fkIntent = Intent(this, MainActivity::class.java)
            startActivity(fkIntent)
        }
        buttonLK.setOnClickListener{
            val galIntent = Intent(this, PersonalAccountActivity::class.java)
            startActivity(galIntent)
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPhotos.layoutManager = layoutManager


        rvPhotos.adapter=photoAdapter

        addPhoto.setOnClickListener {
            val fkIntent = Intent(this, AddPhotoProgressActivity::class.java)
            startActivity(fkIntent)
        }
        loadPhotos()


    }
    private fun deletePhoto(photo: Photo) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Удаление фото")
        alertDialog.setMessage("Вы действительно хотите удалить фото?")

        alertDialog.setPositiveButton("Да") { _, _ ->
            // Delete the photo here
            val authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcnR5b20xIiwiaWF0IjoxNzE4NjI4NTY4LCJleHAiOjE3MTkyMzMzNjh9.m4PNvxZSyLoPvZ4Aj5B4W_CPDN1lvH2SDdqQ0TsqUis"
            val retrofit = Retrofit.Builder()
                .baseUrl("http://212.113.121.36:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(GalleryApi::class.java)
            val call = service.deletePhotoById(authToken, photo.id)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.isSuccessful) {
                        photos.remove(photo)
                        photoAdapter.notifyDataSetChanged()
                    } else {
                        Log.e("Error", "Failed to delete photo")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("NetworkError", "Failed to execute request", t)
                }
            })
        }

        alertDialog.setNegativeButton("Нет") { _, _ ->
            // Do nothing
        }

        alertDialog.show()
    }
private fun loadPhotos() {
    if (photos.isEmpty()) {

        val thread = Thread {

            val authToken =
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBcnR5b20xIiwiaWF0IjoxNzE4NjI4NTY4LCJleHAiOjE3MTkyMzMzNjh9.m4PNvxZSyLoPvZ4Aj5B4W_CPDN1lvH2SDdqQ0TsqUis" // Replace with your actual auth token

            val retrofit = Retrofit.Builder()
                .baseUrl("http://212.113.121.36:8080") // Replace with your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(GalleryApi::class.java)

            val call = service.getPhoto(authToken)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    p0: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val json = responseBody.string()
                            val photosJson = JSONArray(json)

                            for (i in 0 until photosJson.length()) {
                                val photoJson = photosJson.getJSONObject(i)
                                val photoId = photoJson.getString("id")
                                val call = service.getPhotoById(authToken, photoId)

                                call.enqueue(object : Callback<ResponseBody> {
                                    override fun onResponse(
                                        p0: Call<ResponseBody>,
                                        response: Response<ResponseBody>

                                    ) {

                                        if (response.isSuccessful) {
                                            val responseBody = response.body()
                                            if (responseBody != null) {
                                                val bitmap =
                                                    BitmapFactory.decodeStream(responseBody.byteStream())
                                                val weight = photoJson.getString("weight")
                                                val dateTime = photoJson.getString("dateTime")
                                                val inputFormat =
                                                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                                val outputFormat =
                                                    SimpleDateFormat("d MMMM yyyy", Locale("ru"))
                                                val id = photoJson.getString("id")
                                                val date = inputFormat.parse(dateTime)
                                                val formattedDate = outputFormat.format(date)


                                                val photo = Photo(
                                                    weight = "${weight} кг",
                                                    dateTime = formattedDate,
                                                    image = bitmap,
                                                    id = id
                                                )
                                                photos.add(photo)
                                                photoAdapter.notifyDataSetChanged()
                                            }
                                        }
                                    }

                                    override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                                        Log.e("NetworkError", "Failed to execute request", p1)
                                        Toast.makeText(
                                            applicationContext,
                                            "Network Error: ${p1.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                            }


                        } else {
                            Toast.makeText(
                                this@GalleryActivity,
                                "Error uploading photo ${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                    Log.e("NetworkError", "Failed to execute request", p1)
                    Toast.makeText(
                        applicationContext,
                        "Network Error: ${p1.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        thread.start()
    }
}

}