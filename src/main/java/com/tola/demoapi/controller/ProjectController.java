package com.tola.demoapi.controller;


import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.model.response.ApiResponse;
import com.tola.demoapi.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Get all project by user")
    public ResponseEntity<?> getAllProjectsByUser() {
        ApiResponse<?> response = ApiResponse.builder()
                .message("User registered successfully")
                .payload(projectService.getAllProjectsByUser())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest projectRequest) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Create project successfully")
                .payload(projectService.createProject(projectRequest))
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
