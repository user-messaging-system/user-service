package com.user_messaging_system.user_service.api.output;

import java.util.List;

public record UserRegisterOutput(
        String name,
        String lastName,
        String email,
        List<String> roles,
        String accessToken
) implements UserBaseOutput {}