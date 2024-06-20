package com.example.lightweight.retrofit

import com.example.lightweight.Models.FoodItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FoodItemApi {
    @GET("/product/manage")
    fun getOwnProducts(@Header("Authorization") authToken: String): Call<List<FoodItem>>

    @POST("/product")
    fun addOwnProduct(@Header("Authorization") authToken: String, @Body foodItem: FoodItem): Call<FoodItem>

    @DELETE("/product/{id}")
    fun deleteOwnProduckt(@Header("Authorization") authToken: String,@Path("id") productId: String): Call<Void>

    @GET("/product/all")
    fun getAllProducts(@Header("Authorization") authToken: String): Call<List<FoodItem>>

}