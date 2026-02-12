package com.tola.demoapi.model.entities;


import com.tola.demoapi.model.enums.PriorityStatus;
import com.tola.demoapi.model.response.TaskResponse;
import com.tola.demoapi.model.response.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.tola.demoapi.model.enums.Status;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity
@Table(name = "tasks")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private PriorityStatus priorityStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Long assigner;

    private List<User> assignees;

    private LocalDate assignedAt;
    private LocalDate dueAt;

    @CreatedBy
    private Long createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserTask> userTasks = new ArrayList<>();

    public TaskResponse taskResponse(User assigner,List<User> assignees) {
        return TaskResponse.builder()
                .id(id)
                .name(name)
                .description(description)
                .status(status)
                .priorityStatus(priorityStatus)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .dueAt(dueAt)
                .assignedAt(assignedAt)
                .assigner(assigner.toResponse(null))
                .assignees(assignees.stream().map(user -> user.toResponse(null)).collect(Collectors.toList()))
                .build();
    }
}
