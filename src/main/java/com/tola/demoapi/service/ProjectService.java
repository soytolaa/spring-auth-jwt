package com.tola.demoapi.service;

import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.model.response.ProjectResponse;
import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<ProjectResponse> getAllProjectsByUser();

    ProjectResponse createProject(ProjectRequest projectRequest);

    ProjectResponse updateProject(UUID id, ProjectRequest projectRequest);

    ProjectResponse addUserToProjectByEmail(UUID id, String email);

    ProjectResponse deactivateProject(UUID id);

    ProjectResponse activateProject(UUID id);

    Boolean deleteProject(UUID id);
}
