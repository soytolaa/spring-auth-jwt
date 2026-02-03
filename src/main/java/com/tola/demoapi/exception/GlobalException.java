package com.tola.demoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import com.tola.demoapi.utils.ApiResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        ApiResponse<?> response = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        ApiResponse<?> response = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException ex) {
        ApiResponse<?> response = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .statusCode(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
        ApiResponse<?> response = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
