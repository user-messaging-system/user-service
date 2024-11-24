package com.user_messaging_system.user_service.dto;

import java.util.List;

public record UserRegisterDTO (
       String name,
       String lastName,
       String email,
       List<String> roles,
       String accessToken
){ }
