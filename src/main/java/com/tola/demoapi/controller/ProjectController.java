package com.tola.demoapi.controller;

import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.utils.ApiResponse;
import com.tola.demoapi.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Project", description = "Project API for project management")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
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

    @PostMapping("/create")
    @Operation(summary = "Create project")
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
    @Operation(summary = "Update project")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectRequest projectRequest) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Update project successfully")
                .payload(projectService.updateProject(id, projectRequest))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/add-user")
    @Operation(summary = "Add user to project by email")
    public ResponseEntity<?> addUserToProjectByEmail(@PathVariable Long id, @RequestParam String email) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Add user to project successfully")
                .payload(projectService.addUserToProjectByEmail(id, email))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/active-deactive")
    @Operation(summary = "Active and deactivate project")
    public ResponseEntity<?> activeAndDeactiveProject(@PathVariable Long id, @RequestParam Boolean isActive) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Active and deactivate project successfully")
                .payload(projectService.activeAndDeactiveProject(id, isActive))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Delete project successfully")
                .payload(projectService.deleteProject(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/project-users")
    @Operation(summary = "Get users in project by project id")
    public ResponseEntity<?> getUserInProject(@PathVariable Long id) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Get project user by project id successfully")
                .payload(projectService.getUserInProject(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/code")
    @Operation(summary = "Get users in project by project id")
    public ResponseEntity<?> joinProjectByCode(@RequestParam UUID code) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Join project by project code successfully")
                .payload(projectService.joinProjectByCode(code))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
