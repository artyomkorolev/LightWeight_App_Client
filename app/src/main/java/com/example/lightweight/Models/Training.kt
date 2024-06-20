package com.example.lightweight.Models

import java.io.Serializable
import java.util.UUID

data class Training(
    val startTime: String,
    val endTime: String,
    val exercises :Map<UUID,Double>
): Serializable{
    fun toGetTraining(exercises: List<Pair<Exercize, Double>>):GetTraining{
        val exerList = exercises.map {(exercize,quantity) ->
            Exersise(exercise = exercize,quantity=quantity)

        }
        return GetTraining(id = "1",startTime=startTime,endTime=endTime,exercises=exerList)
    }
}
