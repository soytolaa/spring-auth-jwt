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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Product", description = "Product API for product management")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products")
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
    @Operation(summary = "Create a new product")
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

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "id") Integer id,
            @RequestBody ProductRequest productRequest) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Update a product successfully")
                .payload(productService.updateProduct(id, productRequest))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete product successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by id")
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") Integer id) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Get a product by id successfully")
                .payload(productService.getProductById(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/search")
    @Operation(summary = "Search a product by name")
    public ResponseEntity<?> searchProduct(@RequestParam(name = "keyword") String keyword) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Search product by name successfully")
                .payload(productService.searchProduct(keyword))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
