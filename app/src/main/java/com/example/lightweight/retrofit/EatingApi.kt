package com.example.lightweight.retrofit

import com.example.lightweight.Models.Eating
import com.example.lightweight.Models.FoodItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID

interface EatingApi {
    @POST("/eating")
    fun addEating(@Header("Authorization") authToken: String,
                  @Body eating: Eating
    ): Call<Void>
    @GET("/eating/byDate")
    fun getEatingByDate(@Header("Authorization") authToken: String):Call<ArrayList<Eating>>
}