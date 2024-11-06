package com.user_messaging_system.user_service.dto;

import java.util.List;

public record UserDTO(
        String id,
        String email,
        String name,
        String lastName,
        String password,
        List<String> roles
) {}
