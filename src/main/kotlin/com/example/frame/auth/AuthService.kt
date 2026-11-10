package com.example.frame.auth

import com.example.frame.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Locale

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    
    
    fun signup(request: SignupRequest): SignupResponse {
        
        val email = request.email.trim().lowercase(Locale.ROOT)
        
        if (userRepository.existsByEmail(email)) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Email is already registered"
            )
        }
        
        if (request.password.toByteArray(Charsets.UTF_8).size > 72) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Password must be at most 72 UTF-8 bytes"
            )
        }
        
    }
    
}