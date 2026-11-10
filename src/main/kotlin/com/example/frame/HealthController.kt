package com.example.frame

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HealthController {
    
    @GetMapping("/health")
    fun health(): String {
        return "Frame backend is running"
    }
}