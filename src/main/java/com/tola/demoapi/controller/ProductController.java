package com.tola.demoapi.controller;

import com.tola.demoapi.model.request.ProductRequest;
import com.tola.demoapi.model.response.ApiResponse;
import com.tola.demoapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Get all products successfully")
                .payload(productService.getAllProducts())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> createProducts(@RequestBody ProductRequest productRequest) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Create a product successfully")
                .payload(productService.createProducts(productRequest))
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
