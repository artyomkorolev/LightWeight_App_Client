package com.example.lightweight

import java.io.Serializable

data class FoodItem(
     val name:String,
     val calories:String,
     val proteins:String,
     val fats:String,
     val Carbs:String,
     var isSaved: Boolean = false

):Serializable {
}