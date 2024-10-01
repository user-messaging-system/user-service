package com.user_messaging_system.user_service.service;

import java.util.List;

public interface JWTService {
    String generateJwtToken(String email, List<String> roles);
}
