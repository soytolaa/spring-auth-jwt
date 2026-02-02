package com.tola.demoapi.service;

import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.model.response.ProjectResponse;
import com.tola.demoapi.model.response.UserResponse;
import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getAllProjectsByUser();

    ProjectResponse createProject(ProjectRequest projectRequest);

    ProjectResponse updateProject(Long id, ProjectRequest projectRequest);

    ProjectResponse addUserToProjectByEmail(Long id, String email);

    ProjectResponse activeAndDeactiveProject(Long id, Boolean isActive);

    Boolean deleteProject(Long id);

    List<UserResponse> getUserInProject(Long id);
}
