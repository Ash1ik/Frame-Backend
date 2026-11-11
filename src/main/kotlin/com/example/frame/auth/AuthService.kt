package com.example.frame.auth

import com.example.frame.auth.login.LoginRequest
import com.example.frame.auth.login.LoginResponse
import com.example.frame.auth.signup.SignupRequest
import com.example.frame.auth.signup.SignupResponse
import com.example.frame.user.User
import com.example.frame.user.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
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
        
        val user = User(
            name = request.name.trim(),
            email = email,
            passwordHash = passwordEncoder.encode(request.password)!!
        )
        
        val saveUser = try {
            userRepository.saveAndFlush(user)
        } catch (exception: DataIntegrityViolationException) {
            if (userRepository.existsByEmail(email)) {
                throw ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already registered"
                )
            }
            throw exception
        }
        
        return SignupResponse(
            id = checkNotNull(saveUser.id),
            name = saveUser.name,
            email = saveUser.email
        )
        
    }

    
    fun login(request: LoginRequest): LoginResponse {
        
        val email = request.email.trim().lowercase(Locale.ROOT)
        
        val user = userRepository.findByEmail(email) ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
            )
            
        val passwordMatches = passwordEncoder.matches(
            request.password,
            user.passwordHash
        )
        
        if (passwordMatches) {
            return LoginResponse(
                id = checkNotNull(user.id),
                name = user.name,
                email = user.email
            )
        } else {
            throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
            )
        }
    
    }
    
    
}