package com.example.lightweight.Models

import android.graphics.Bitmap

data class Photo(
    val id: String,
    val image: Bitmap,
    val dateTime:String,
    val weight: String,
//    val file: Multipart
) {
}
