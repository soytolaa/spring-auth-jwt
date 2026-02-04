package com.tola.demoapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.tola.demoapi.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import com.tola.demoapi.model.request.TaskRequest;
import com.tola.demoapi.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import com.tola.demoapi.model.request.UserTaskRequest;
import com.tola.demoapi.model.enums.Status;
import com.tola.demoapi.model.enums.PriorityStatus;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task", description = "Task API for task management")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    @Operation(summary = "Create a new task")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Task created successfully")
                .payload(taskService.createTask(taskRequest))
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/assign-user")
    @Operation(summary = "Update a task")
    public ResponseEntity<?> assignUserToTask(@RequestBody UserTaskRequest userTaskRequest) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("User added to task successfully")
                .payload(taskService.addUserToTask(userTaskRequest))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/remove-user")
    @Operation(summary = "Remove user from task")
    public ResponseEntity<?> removeUserFromTask(@RequestParam UserTaskRequest userTaskRequest) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("User removed from task successfully")
                .payload(taskService.removeUserFromTask(userTaskRequest))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/update-status")
    @Operation(summary = "Update task status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @RequestParam Status status) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Task status updated successfully")
                .payload(taskService.updateTaskStatus(id, status))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/update-priority-status")
    @Operation(summary = "Update task priority status")
    public ResponseEntity<?> updateTaskPriorityStatus(@PathVariable Long id,
            @RequestParam PriorityStatus priorityStatus) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Task priority status updated successfully")
                .payload(taskService.updateTaskPriorityStatus(id, priorityStatus))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/update-due-date")
    @Operation(summary = "Update task due date")
    public ResponseEntity<?> updateTaskDueDate(
            @PathVariable Long id,
            @Parameter(description = "Due date and time for the task", required = true, schema = @Schema(type = "string", format = "date-time", example = "2026-02-01")) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Task due date updated successfully")
                .payload(taskService.updateTaskDueDate(id, dueDate))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Task deleted successfully")
                .payload(taskService.deleteTask(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Task retrieved successfully")
                .payload(taskService.getTaskById(id))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get all tasks by project id")
    public ResponseEntity<?> getAllTasksByProjectId(@PathVariable Long projectId) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("Tasks retrieved successfully")
                .payload(taskService.getAllTasksByProjectId(projectId))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
