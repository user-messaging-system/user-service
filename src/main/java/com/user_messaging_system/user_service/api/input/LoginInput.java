package com.user_messaging_system.user_service.api.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import static com.user_messaging_system.core_library.common.constant.ValidationConstant.*;

@Validated
public record LoginInput(
        @NotBlank
        @Email(message = INVALID_EMAIL)
        @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH, message = INVALID_EMAIL)
        String email,

        @NotBlank
        @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = INVALID_PASSWORD)
        String password
) {}