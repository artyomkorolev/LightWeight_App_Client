package com.example.lightweight.Models

import java.io.Serializable
import java.util.UUID

data class Eating (
    val dateTime :String,
//    val calories: String,
//    val proteins: String,
//    val curbs:String,
//    val fats:String,
    val products:Map<UUID,Double>
):Serializable{

}
