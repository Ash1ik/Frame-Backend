package com.example.frame.auth.login

data class LoginResponse(
    val id: Long,
    val name: String,
    val email: String,
    val accessToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long = 900
)