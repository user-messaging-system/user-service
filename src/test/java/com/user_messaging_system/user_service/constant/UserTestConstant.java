package com.user_messaging_system.user_service.constant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class UserTestConstant {
    private UserTestConstant() {
        throw new AssertionError();
    }

    public static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJidXJha3lhcGljaTc0N0BnbWFpbC5jb20iLCJ1c2VySWQiOiJjNTZjNWU0NC0xZGI5LTQ1YzktYmRhMy04NjA3MjcxM2FjOTciLCJyb2xlcyI6WyJVU0VSIl0sImlhdCI6MTczMzEyOTE1NiwiZXhwIjoxNzMzNTYxMTU2fQ.GgZexlWe-lGgz4xBUrxqSdKpi_Xnus45rFd_-7dOXR8";
    public static final String ID = UUID.randomUUID().toString();
    public static final String INVALID_FORMAT_ID = "invalid_format_id";
    public static final String NAME = "Burak";
    public static final String LASTNAME = "YAPICI";
    public static final String EMAIL = "burakyapici747@gmail.com";
    public static final String INVALID_FORMAT_EMAIL = "burakyapici747gmail.com";
    public static final String NOT_EXIST_EMAIL = "test@gmail.com";
    public static final String PASSWORD = "12345678";
    public static final boolean MAIL_VERIFIED = false;
    public static final List<String> ROLES = List.of("USER");
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final LocalDateTime UPDATED_AT = LocalDateTime.now();

}
