package com.user_messaging_system.user_service.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.user_messaging_system.user_service.service.JWTService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JWTServiceImpl implements JWTService {
    private String secretKey = "929dfc305899415c31f576fc46d8bd8b81b7e1fb6c256bbacfe0656c1da7bf11";
    public String generateJwtToken(String email, List<String> roles){
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(email)
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000))
                .sign(algorithm);
    }
}
