package com.tola.demoapi.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tola.demoapi.config.BeanConfig;
import com.tola.demoapi.model.enums.Role;
import com.tola.demoapi.model.response.ProjectResponse;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "projects")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private UUID code;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private Long createdBy;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserProject> userProjects = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();


    public ProjectResponse toResponse(Long userId) {
        return ProjectResponse.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .createdBy(userId)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .code(this.code)
                .membersCount(null)
                .build();
    }
}