package com.user_messaging_system.user_service.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.user_messaging_system.core_library.common.annotation.LogExecution;
import com.user_messaging_system.core_library.exception.UnauthorizedException;
import com.user_messaging_system.core_library.exception.UserNotFoundException;
import com.user_messaging_system.core_library.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static com.user_messaging_system.core_library.common.constant.ErrorConstant.NO_ROOT_CAUSE_AVAILABLE;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @LogExecution
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
        UserNotFoundException exception,
        WebRequest request
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .errors(List.of(exception.getCause() != null ? exception.getCause().getMessage() : NO_ROOT_CAUSE_AVAILABLE))
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @LogExecution
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception,
        WebRequest request
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .errors(List.of(exception.getCause() != null ? exception.getCause().getMessage() : NO_ROOT_CAUSE_AVAILABLE))
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @LogExecution
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistException(
        EmailAlreadyExistException exception,
        WebRequest request
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .errors(List.of(exception.getCause() != null ? exception.getCause().getMessage() : NO_ROOT_CAUSE_AVAILABLE))
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
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .errors(List.of(exception.getCause() != null ? exception.getCause().getMessage() : NO_ROOT_CAUSE_AVAILABLE))
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @LogExecution
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
        UnauthorizedException exception,
        WebRequest request
    ){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .errors(List.of(exception.getCause() != null ? exception.getCause().getMessage() : NO_ROOT_CAUSE_AVAILABLE))
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @LogExecution
    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(
        JWTVerificationException exception,
        WebRequest request
    ){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .errors(List.of(exception.getCause() != null ? exception.getCause().getMessage() : NO_ROOT_CAUSE_AVAILABLE))
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getDescription(false))
                .build()
            );
    }

    @LogExecution
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException exception, WebRequest request){
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse.Builder()
                .message(exception.getMessage())
                .errors(List.of(exception.getCause() != null ? exception.getCause().getMessage() : NO_ROOT_CAUSE_AVAILABLE))
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getDescription(false))
                .build()
            );
    }
}
