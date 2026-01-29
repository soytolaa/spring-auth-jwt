package com.tola.demoapi.model.entities;

import com.tola.demoapi.model.enums.PriorityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.tola.demoapi.model.enums.Status;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private PriorityStatus priorityStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID assigner;
    private List<UUID> assignees;
    private LocalDateTime assignedAt;
    private LocalDateTime dueAt;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;
}
