package com.tola.demoapi.model.response;

import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {
    private String message;
    private T payload;
    private HttpStatus status;
    private Integer statusCode;
    private LocalDateTime timestamp;
}