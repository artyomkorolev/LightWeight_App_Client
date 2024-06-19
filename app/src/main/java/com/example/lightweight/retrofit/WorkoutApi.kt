package com.example.lightweight.retrofit

import com.example.lightweight.Models.Eating
import com.example.lightweight.Models.GetEating
import com.example.lightweight.Models.GetTraining
import com.example.lightweight.Models.Training
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkoutApi {
    @POST("/workout")
    fun addTraining(@Header("Authorization") authToken: String,
                  @Body training: Training
    ): Call<Void>

    @GET("/workout/byDate")
    fun getWorkoutByDate(@Header("Authorization") authToken: String,@Query("date") dateTime: String):Call<List<GetTraining>>

    @DELETE("/workout/{id}")
    fun deleteWorkoutbyId(@Header("Authorization") authToken: String,@Path("id") workoutId: String):Call<Void>
}