package com.example.lightweight.retrofit

import com.example.lightweight.Models.Exercize
import com.example.lightweight.Models.FoodItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ExerciseApi {
    @GET("/exercise/manage")
    fun getOwnExercise(@Header("Authorization") authToken: String): Call<List<Exercize>>

    @POST("/exercise")
    fun addOwnExercise(@Header("Authorization") authToken: String, @Body exercize: Exercize) : Call<Exercize>

    @DELETE("/exercise/{id}")
    fun deleteOwnExercise(@Header("Authorization") authToken: String,@Path("id") exerciseId: String) : Call<Void>

    @GET("/exercise")
    fun getAllExercise():Call<List<Exercize>>
}