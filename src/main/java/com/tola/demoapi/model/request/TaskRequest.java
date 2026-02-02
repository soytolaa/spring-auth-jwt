package com.tola.demoapi.model.request;

import com.tola.demoapi.model.enums.PriorityStatus;
import com.tola.demoapi.model.enums.Status;
import java.util.List;
import java.time.LocalDateTime;

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
    private LocalDateTime assignedAt;
    private LocalDateTime dueAt;
}
