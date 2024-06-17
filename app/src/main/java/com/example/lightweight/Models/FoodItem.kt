package com.example.lightweight.Models

import java.io.Serializable

data class FoodItem(
     val id:String,
     val name:String,
     val calories:Int,
     val protein:Double,
     val fats:Double,
     val carbohydrates:Double,
     var isSaved: Boolean = false

):Serializable {
}