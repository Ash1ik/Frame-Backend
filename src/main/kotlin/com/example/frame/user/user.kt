package com.example.frame.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
open class User(
    
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    
    @field:Column(nullable = false)
    open var name: String,
    
    @field:Column(nullable = false, unique = true)
    open var email: String,
    
    @field:Column(name = "password_hash", nullable = false)
    open var password: String
    
)
