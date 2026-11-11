package com.example.frame.auth.login

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest (
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is invalid")
    val email: String,
    
    @field:NotBlank(message = "Password is required")
    val password: String
)