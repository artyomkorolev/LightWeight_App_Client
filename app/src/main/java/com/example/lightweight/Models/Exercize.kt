package com.example.lightweight.Models

import java.io.Serializable

data class Exercize(
    val id : String,
    val name: String,
    val unit: String,
    var count: String,
    var isSaved: Boolean = false,
    var hideInput: Boolean= false,
    val available: Boolean = true
):Serializable {
}