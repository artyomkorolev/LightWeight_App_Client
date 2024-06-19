package com.example.lightweight.Models

import java.io.Serializable
import java.util.UUID

data class Eating (
    val id:String,
    val dateTime :String,
    val products:Map<UUID,Double>
):Serializable{
//
    fun toGetEating(foodItems: List<Pair<FoodItem, Double>>): GetEating {
    val productList = foodItems.map { (foodItem, quantity) ->
        Products(product = foodItem, quantity = quantity)
    }
    return GetEating(id = id, dateTime = dateTime, products = productList)
}
}

