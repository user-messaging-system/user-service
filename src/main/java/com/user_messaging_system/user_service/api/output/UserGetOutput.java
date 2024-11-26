package com.user_messaging_system.user_service.api.output;

import java.util.List;

public record UserGetOutput(
        String id,
        String email,
        String name,
        String lastName,
        String password,
        List<String> roles
) implements UserBaseOutput{}