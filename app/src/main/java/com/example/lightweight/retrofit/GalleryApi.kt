package com.example.lightweight.retrofit

import com.example.lightweight.Models.Photo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface GalleryApi {
@Multipart
@POST("/photo")
fun addPhoto( @Header("Authorization") authToken: String,
              @Part image: MultipartBody.Part, // Файл изображения как Multipart
              @Part("dateTime") dateTime: String,
              @Part("weight") weight: Int
):Call<Photo>
}