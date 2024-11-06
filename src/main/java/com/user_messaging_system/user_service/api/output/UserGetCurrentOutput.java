package com.user_messaging_system.user_service.api.output;

import java.util.List;

public record UserGetCurrentOutput(
        String id,
        String name,
        String lastName,
        String email,
        String password,
        List<String> roles
) implements UserBaseOutput{}