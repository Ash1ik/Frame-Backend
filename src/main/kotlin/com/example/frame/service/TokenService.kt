package com.example.frame.service

import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TokenService (
    private val jwtEncoder: JwtEncoder
) {
    
    fun createAccessToken(userId: Long): String {
        val now = Instant.now()
        
        val claims = JwtClaimsSet.builder()
            .issuer("frame-api")
            .subject(userId.toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(15*60L))
            .build()
            
        val header = JwsHeader
            .with(MacAlgorithm.HS256)
            .build()
            
        return jwtEncoder.encode(
            JwtEncoderParameters.from(header, claims)
        ).tokenValue
    }
    
}
