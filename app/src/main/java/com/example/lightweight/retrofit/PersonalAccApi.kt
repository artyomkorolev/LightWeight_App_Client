package com.example.lightweight.retrofit

import com.example.lightweight.AuthReg.AuthResponse
import com.example.lightweight.Models.User
import com.example.lightweight.AuthReg.UserAuth
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface PersonalAccApi {
    @GET("/user/info")
    fun getUserInfo(@Header("Authorization") authToken: String): Call<User>

    @PUT("/user/updateData")
    fun postUpdateinfo(@Header("Authorization") authToken: String,@Body user: User): Call<Void>

    @POST("/user/authenticate")
    fun authenticate(@Body user: UserAuth):Call<AuthResponse>

}