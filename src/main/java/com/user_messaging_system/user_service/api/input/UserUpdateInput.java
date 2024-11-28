package com.user_messaging_system.user_service.api.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import static com.user_messaging_system.core_library.common.constant.ValidationConstant.*;

@Validated
public record UserUpdateInput(
        @NotBlank
        @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = INVALID_NAME)
        String name,
        @NotBlank
        @Size(min = LASTNAME_MIN_LENGTH, max = LASTNAME_MAX_LENGTH, message = INVALID_LAST_NAME)
        String lastName,
        @NotBlank
        @Email(message = INVALID_EMAIL)
        String email
) {}