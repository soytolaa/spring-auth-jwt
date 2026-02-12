package com.tola.demoapi.mapper;

import com.tola.demoapi.model.entities.Task;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.response.TaskResponse;
import com.tola.demoapi.model.response.UserResponse;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.request.TaskRequest;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final UserMapper userMapper;
    public List<TaskResponse> toResponse(List<Task> tasks, List<User> assignees, Long projectId, User createdBy) {
        return tasks.stream()
                .map(task -> toResponse(task, assignees, projectId, createdBy))
                .collect(Collectors.toList());
    }

    public TaskResponse toResponse(Task task, List<User> assignees, Long projectId, User createdBy) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .priorityStatus(task.getPriorityStatus())
                .status(task.getStatus())
                .assignees(userMapper.toResponse(assignees))
                .createdBy(userMapper.toResponse(createdBy))
                .build();
    }

    public Task toEntity(TaskRequest taskRequest, Project project, Long createdBy, Long assigner) {
        return Task.builder()
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .status(taskRequest.getStatus())
                .priorityStatus(taskRequest.getPriorityStatus())
                .project(project)
                .createdBy(createdBy) 
                .assigner(assigner)
                .assignedAt(LocalDate.now())
                .dueAt(taskRequest.getDueAt())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
