package com.tola.demoapi.model.request;


import com.tola.demoapi.model.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String name;
    private String description;

    public Project toEntity(Long userId, UUID code) {
        return Project.builder()
                .name(this.name)
                .description(this.description)
                .createdBy(userId)
                .code(code)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isActive(true)
                .build();
    }

    public void toUpdateEntity(Project project) {
        project.setName(this.name);
        project.setDescription(this.description);
        project.setUpdatedAt(LocalDateTime.now());
    }

}
