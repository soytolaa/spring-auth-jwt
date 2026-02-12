package com.tola.demoapi.model.request;

import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.entities.UserProject;
import com.tola.demoapi.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProjectRequest {
    User user;
    Project project;
    public UserProject toEntity(){
        return UserProject.builder()
                .user(user)
                .project(project)
                .joinedAt(LocalDateTime.now())
                .role(Role.ADMIN)
                .build();
    }
}
