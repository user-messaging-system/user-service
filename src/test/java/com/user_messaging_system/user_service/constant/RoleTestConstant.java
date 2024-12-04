package com.user_messaging_system.user_service.constant;

import java.time.LocalDateTime;

public final class RoleTestConstant {
    private RoleTestConstant() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static final String ID = "1";
    public static final String ROLE_NAME = "USER";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final LocalDateTime UPDATED_AT = LocalDateTime.now();
}
