package com.tola.demoapi.model.request;

import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.Task;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.enums.PriorityStatus;
import com.tola.demoapi.model.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;

import com.tola.demoapi.model.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String name;
    private String description;
    private Status status;
    private PriorityStatus priorityStatus;
    private Long projectId;
    private List<Long> assignees;
    private LocalDate assignedAt;
    private LocalDate dueAt;

    public Task toEntity(Project project, Long createdBy, List<User> assignees) {
        return Task.builder()
                .name(name)
                .description(description)
                .priorityStatus(priorityStatus)
                .status(status)
                .project(project)
                .createdBy(createdBy)
                .dueAt(dueAt)
                .assignedAt(assignedAt)
                .assigner(createdBy)
                .assignees(assignees)
                .build();
    }
}
