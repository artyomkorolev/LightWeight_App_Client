package com.example.lightweight.Models

import java.io.Serializable
import java.util.UUID

data class Eating (
    val id:String,
    val dateTime :String,
    val products:Map<UUID,Double>
):Serializable{

}
