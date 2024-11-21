package com.user_messaging_system.user_service.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.user_messaging_system.core_library.exception.UnauthorizedException;
import com.user_messaging_system.core_library.exception.UserNotFoundException;
import com.user_messaging_system.core_library.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
        UserNotFoundException exception,
        WebRequest request
    ){
        logger.error(
            "[ERROR] UserNotFoundException occurred. Status: {}. Path: {}. Message: {}",
            HttpStatus.NOT_FOUND.value(),
            request.getDescription(false),
            exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getCause() != null ? exception.getCause().getMessage() : "No root cause available")
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistException(
        EmailAlreadyExistException exception,
        WebRequest request
    ){
        logger.error(
            "[ERROR] EmailAlreadyExistException occurred. Status: {}. Path: {}. Message: {}",
            HttpStatus.NOT_FOUND.value(),
            request.getDescription(false),
            exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getCause() != null ? exception.getCause().getMessage() : "No root cause available")
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(
        EntityExistsException exception,
        WebRequest request
    ){
        logger.error(
            "[ERROR] EntityExistsException occurred. Status: {}. Path: {}. Message: {}",
            HttpStatus.CONFLICT.value(),
            request.getDescription(false),
            exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getCause() != null ? exception.getCause().getMessage() : "No root cause available")
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
        UnauthorizedException exception,
        WebRequest request
    ){
        logger.error(
            "[ERROR] UnauthorizedException occurred. Status: {}. Path: {}. Message: {}",
            HttpStatus.UNAUTHORIZED.value(),
            request.getDescription(false),
            exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getCause() != null ? exception.getCause().getMessage() : "No root cause available")
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(
        JWTVerificationException exception,
        WebRequest request
    ){
        logger.error(
            "[ERROR] JWTVerificationException occurred. Status: {}. Path: {}. Message: {}",
            HttpStatus.UNAUTHORIZED.value(),
            request.getDescription(false),
            exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getCause() != null ? exception.getCause().getMessage() : "No root cause available")
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException exception, WebRequest request){
        logger.error(
            "[ERROR] JWTException occurred. Status: {}. Path: {}. Message: {}",
            HttpStatus.UNAUTHORIZED.value(),
            request.getDescription(false),
            exception.getMessage()
        );
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .error(exception.getCause() != null ? exception.getCause().getMessage() : "No root cause available")
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getDescription(false))
                .build()
            );
    }
}
