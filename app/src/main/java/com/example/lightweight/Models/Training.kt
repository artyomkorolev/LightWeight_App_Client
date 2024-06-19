package com.example.lightweight.Models

import java.io.Serializable
import java.util.UUID

data class Training(
    val startTime: String,
    val endTime: String,
    val exercises :Map<UUID,Double>
): Serializable
