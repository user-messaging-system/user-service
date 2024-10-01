package com.user_messaging_system.user_service.api.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserInput(
        @Size(min = 3, max = 50)
        String name,
        @Size(min = 3, max = 50)
        String lastName,
        @Email
        String email,
        @Size(min = 8, max = 50)
        String password
) {
}
