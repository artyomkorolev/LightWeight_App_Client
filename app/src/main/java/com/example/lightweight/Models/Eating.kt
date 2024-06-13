package com.example.lightweight.Models

import java.io.Serializable

data class Eating (
    val time :String,
    val calories: String,
    val proteins: String,
    val curbs:String,
    val fats:String
):Serializable{

}
