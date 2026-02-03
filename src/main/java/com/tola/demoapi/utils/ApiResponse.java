package com.tola.demoapi.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private HttpStatus status;
    private Integer statusCode;
    private LocalDateTime timestamp;
}