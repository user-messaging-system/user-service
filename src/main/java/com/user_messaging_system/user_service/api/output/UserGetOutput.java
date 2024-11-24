package com.user_messaging_system.user_service.api.output;

public record UserGetOutput(
        String id,
        String name,
        String lastName,
        String email
) implements UserBaseOutput{}