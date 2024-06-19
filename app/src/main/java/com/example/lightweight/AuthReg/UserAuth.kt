package com.example.lightweight.AuthReg

data class UserAuth (

    val login:String,
    val password: String
)
data class AuthResponse(
    val access_token: String
)
