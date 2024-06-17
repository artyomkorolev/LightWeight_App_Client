package com.example.lightweight.Models

data class Exercize(
    val id : String,
    val name: String,
    val unit: String,
    var isSaved: Boolean = false
) {
}