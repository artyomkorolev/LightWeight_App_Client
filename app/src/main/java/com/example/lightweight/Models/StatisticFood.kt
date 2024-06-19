package com.example.lightweight.Models

import java.io.Serializable

data class StatisticFood (
    val date: String,
    val statValue: Double,
    val statType: String
):Serializable