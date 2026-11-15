package com.example.frame.config

import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.util.Base64
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
class JwtConfig {
    
    @Bean
    fun jwtSecretKey(
        @Value("\${frame.jwt.secret}") secret: String
    ): SecretKey {
        
        val keyBytes = Base64.getDecoder().decode(secret)
        
        require(keyBytes.size >= 32) {
            "JWT secret must contain at least 32 decoded bytes"
        }
        
        return SecretKeySpec(keyBytes, "HmacSHA256")
        
    }
    
    @Bean
    fun jwtEncoder(secretKey: SecretKey): JwtEncoder {
        
        return NimbusJwtEncoder(
            ImmutableSecret<SecurityContext>(secretKey)
        )
        
    }

    @Bean
    fun jwtDecoder(secretKey: SecretKey): JwtDecoder {
        val decoder = NimbusJwtDecoder
        .withSecretKey(secretKey)
        .macAlgorithm(MacAlgorithm.HS256)
        .build()
        
        decoder.setJwtValidator(
            JwtValidators.createDefaultWithIssuer("frame-api")
        )
        
        return decoder
    }
    
    
    
}