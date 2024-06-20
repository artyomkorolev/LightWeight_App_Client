package com.example.lightweight.retrofit

import com.example.lightweight.AuthReg.AuthResponse
import com.example.lightweight.Models.User
import com.example.lightweight.AuthReg.UserAuth
import com.example.lightweight.Models.StatisticFood
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonalAccApi {
    @GET("/user/info")
    fun getUserInfo(@Header("Authorization") authToken: String): Call<User>

    @PUT("/user/updateData")
    fun postUpdateinfo(@Header("Authorization") authToken: String,@Body user: User): Call<Void>

    @POST("/user/authenticate")
    fun authenticate(@Body user: UserAuth):Call<AuthResponse>
    @POST("/user/register")
    fun register(@Body user: UserAuth):Call<AuthResponse>

    @GET("/user/stat/calories")
    fun getStatFood(@Header("Authorization") authToken: String):Call<ArrayList<StatisticFood>>

    @GET("/user/stat/exercise/{id}")
    fun getStatExerciseById(@Header("Authorization") authToken: String,@Path("id") exerciseId: String):Call<ArrayList<StatisticFood>>

    @PUT("/user/updatePassword")
    fun postUpdatePassword(@Header("Authorization") authToken: String,@Query("password") password: String): Call<Void>

}