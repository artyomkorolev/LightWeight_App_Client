package com.example.lightweight.retrofit

import com.example.lightweight.Models.Photo
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface GalleryApi {
@Multipart
@POST("/photo")
fun addPhoto(@Header("Authorization") authToken: String,
             @Query("date") dateTime: String,
             @Query("weight") weight: Int,
             @Part file: MultipartBody.Part
):Call<ResponseBody>

@GET("/photo")
fun getPhoto(@Header("Authorization") authToken: String):Call<ResponseBody>

@GET("/photo/{id}")
fun getPhotoById(@Header("Authorization") authToken: String,@Path("id") photoId: String):Call<ResponseBody>

@DELETE("/photo/{id}")
fun deletePhotoById(@Header("Authorization") authToken: String,@Path("id") photoId: String):Call<Void>


}



