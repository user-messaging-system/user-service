package com.user_messaging_system.user_service.api.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import static com.user_messaging_system.core_library.common.constant.ValidationConstant.*;

@Validated
public record UserRegisterInput(
        @NotBlank(message = NAME_NOT_BLANK)
        @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = INVALID_NAME)
        String name,

        @NotBlank(message = LAST_NAME_NOT_BLANK)
        @Size(min = LASTNAME_MIN_LENGTH, max = LASTNAME_MAX_LENGTH, message = INVALID_LAST_NAME)
        String lastName,

        @NotBlank(message = EMAIL_NOT_BLANK)
        @Email(message = INVALID_EMAIL)
        @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH, message = INVALID_EMAIL)
        String email,

        @NotBlank(message = PASSWORD_NOT_BLANK)
        @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = INVALID_PASSWORD)
        String password
)
{}
