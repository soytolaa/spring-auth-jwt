package com.tola.demoapi.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.tola.demoapi.model.request.StockRequest;
import com.tola.demoapi.model.response.ApiResponse;
import com.tola.demoapi.service.StockService;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StockController {
    private final StockService stockService;

    @GetMapping
    public ResponseEntity<?> getAllStocks() {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Get all stocks successfully")
                .payload(stockService.getAllStocks())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> createStock(@RequestBody StockRequest stockRequest) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Create stock successfully")
                .payload(stockService.createStock(stockRequest))
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable(name = "id") Integer id,
            @RequestBody StockRequest stockRequest) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Update stock successfully")
                .payload(stockService.updateStock(id, stockRequest))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStockById(@PathVariable(name = "id") Integer id) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Get stock by id successfully")
                .payload(stockService.getStockById(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
