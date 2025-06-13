package com.tola.demoapi.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.tola.demoapi.model.request.CategoryRequest;
import com.tola.demoapi.model.response.ApiResponse;
import com.tola.demoapi.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Category", description = "Category API for category management")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<?> getAllCategories() {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Get all categories successfully")
                .payload(categoryService.getAllCategories())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Create category successfully")
                .payload(categoryService.createCategory(categoryRequest))
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "id") Integer id,
            @RequestBody CategoryRequest categoryRequest) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Update category successfully")
                .payload(categoryService.updateCategory(id, categoryRequest))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by id")
    public ResponseEntity<?> getCategoryById(@PathVariable(name = "id") Integer id) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Get category by id successfully")
                .payload(categoryService.getCategoryById(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
