package com.user_messaging_system.user_service.api.output;

public record UserGetOutput(
        String name,
        String lastName,
        String email
) implements UserBaseOutput{}