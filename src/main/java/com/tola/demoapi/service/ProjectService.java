package com.tola.demoapi.service;

import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.request.ProjectRequest;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjectsByUser();

    Project createProject(ProjectRequest projectRequest);

}
