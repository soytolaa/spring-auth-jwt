package com.tola.demoapi.model.entities;

import com.tola.demoapi.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_projects")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private LocalDateTime joinedAt;

    @Enumerated(EnumType.STRING)
    private Role role;
}

