package com.user_messaging_system.user_service.api.output;

import java.util.List;

public record UserOutput (
        String email,
        String name,
        String lastName,
        String password,
        List<String> roles
) implements UserBaseOutput{}