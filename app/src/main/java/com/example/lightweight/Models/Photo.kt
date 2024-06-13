package com.example.lightweight.Models

import retrofit2.http.Url

data class Photo(
    val image: String,
    val dateTime:String,
    val weight: Int,
//    val file: Multipart
) {
}
