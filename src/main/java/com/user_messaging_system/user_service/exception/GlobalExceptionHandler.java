package com.user_messaging_system.user_service.exception;

import com.user_messaging_system.core_library.response.ErrorResponse;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getCause().getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistException(EmailAlreadyExistException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getCause().getMessage())
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistsException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false))
                .build()
            );
    }
}
