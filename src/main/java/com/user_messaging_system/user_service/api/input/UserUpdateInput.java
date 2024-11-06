package com.user_messaging_system.user_service.api.input;

import io.micrometer.common.lang.Nullable;

public record UserUpdateInput(
        @Nullable
        String name,
        @Nullable
        String lastName,
        @Nullable
        String email
) {}