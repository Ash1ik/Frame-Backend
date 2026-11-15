package com.example.frame.controller

import com.example.frame.service.UserService
import com.example.frame.user.ProfileResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
class UserController(
    private val userService: UserService
) {
    
    @GetMapping("/me")
    fun me(
        @AuthenticationPrincipal jwt: Jwt
    ): ProfileResponse {
        val userId = jwt.subject?.toLongOrNull()
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid token subject"
            )
        return userService.getProfile(userId)
    }
    
}