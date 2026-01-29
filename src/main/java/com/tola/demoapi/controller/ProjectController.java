package com.tola.demoapi.controller;


import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.utils.ApiResponse;
import com.tola.demoapi.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable UUID id,@RequestBody ProjectRequest projectRequest) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Update project successfully")
                .payload(projectService.updateProject(id,projectRequest))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{id}/add-user")
    public ResponseEntity<?> addUserToProjectByEmail(@PathVariable UUID id, @RequestParam String email) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Add user to project successfully")
                .payload(projectService.addUserToProjectByEmail(id, email))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateProject(@PathVariable UUID id) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Deactivate project successfully")
                .payload(projectService.deactivateProject(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateProject(@PathVariable UUID id) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Activate project successfully")
                .payload(projectService.activateProject(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable UUID id) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Delete project successfully")
                .payload(projectService.deleteProject(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
