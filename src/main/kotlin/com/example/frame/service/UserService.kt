package com.example.frame.service

import com.example.frame.user.ProfileResponse
import com.example.frame.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepository: UserRepository
) {
    
    fun getProfile(userId: Long): ProfileResponse {
        val user = userRepository.findById(userId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User no longer exists"
                )
            }
        return ProfileResponse(
            id = checkNotNull(user.id),
            name = user.name,
            email = user.email
        )
    }
    
}