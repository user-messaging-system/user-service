package com.user_messaging_system.user_service.api.output;

public record UserUpdateOutput(
        String name,
        String lastName,
        String email
) {}
