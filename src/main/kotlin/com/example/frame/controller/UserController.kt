package com.example.frame.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {
    
    @GetMapping("/me")
    fun me(
        @AuthenticationPrincipal jwt: Jwt
    ): Map<String, String?> {
        return mapOf(
            "userId" to jwt.subject
        )
    }
    
}