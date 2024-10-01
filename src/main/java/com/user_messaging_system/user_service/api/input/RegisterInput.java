package com.user_messaging_system.user_service.api.input;

public record RegisterInput(
        String name,
        String lastName,
        String email,
        String password
)
{}
