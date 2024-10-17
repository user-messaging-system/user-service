package com.user_messaging_system.user_service.common;

import com.user_messaging_system.core_library.response.SuccessResponse;
import org.springframework.http.HttpStatus;

public class SuccessResponseBuilder {

    public static <T> SuccessResponse<T> buildSuccessResponse(T data, HttpStatus status) {
         return new SuccessResponse<>(
                "Success",
                data,
                String.valueOf(status)
        );
    }
}
