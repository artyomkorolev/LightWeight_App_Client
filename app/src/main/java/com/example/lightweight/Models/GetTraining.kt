package com.example.lightweight.Models

import java.io.Serializable

data class GetTraining(
    val id : String,
    val startTime : String,
    val endTime:String,
    val exercises: List<Exersise>
):Serializable

data class Exersise(
    val exercise: Exercize,
    val quantity: Double
):Serializable
