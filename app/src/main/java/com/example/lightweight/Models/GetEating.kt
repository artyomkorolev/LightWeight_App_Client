package com.example.lightweight.Models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.UUID

data class GetEating (
    val id : String,
    val dateTime : String,
    val products : List<Products>
):Serializable
data class Products(
    val product: FoodItem,
    val quantity: Double
): Serializable