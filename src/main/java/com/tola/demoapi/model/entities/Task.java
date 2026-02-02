package com.tola.demoapi.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tola.demoapi.model.enums.PriorityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.tola.demoapi.model.enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

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
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private PriorityStatus priorityStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long assigner;
    private LocalDate assignedAt;
    private LocalDate dueAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonIgnoreProperties({ "tasks", "userProjects" })
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "task" })
    @Builder.Default
    private List<UserTask> userTasks = new ArrayList<>();
}
