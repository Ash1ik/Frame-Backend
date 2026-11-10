package com.example.frame.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest(
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 100, message = "Name must be almost 100 characters")
    val name: String,
    
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is invalid")
    @field:Size(max = 254, message = "Email is too long")
    val email: String,
    
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, max = 64, message = "Password must be 8-64 characters")
    val password: String
)