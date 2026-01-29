package com.tola.demoapi.model.response;

import java.util.List;
import java.time.LocalDateTime;
import com.tola.demoapi.model.enums.PriorityStatus;
import com.tola.demoapi.model.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private PriorityStatus priorityStatus;
    private Status status;
    private Long projectId;
    private Long assigner;
    private List<Long> assignees;
    private LocalDateTime assignedAt;
    private LocalDateTime dueAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
