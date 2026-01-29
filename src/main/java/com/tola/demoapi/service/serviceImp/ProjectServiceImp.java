package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.config.BeanConfig;
import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.repository.ProjectRepository;
import com.tola.demoapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectService {
    private final ProjectRepository projectRepository;
    private final BeanConfig beanConfig;
    @Override
    public List<Project> getAllProjectsByUser() {
        List<Project> projects = projectRepository.findAllByCreatedBy(null);
        return projects;
    }

    @Override
    public Project createProject(ProjectRequest projectRequest) {
        Project project = Project.builder()
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .createdAt(LocalDateTime.now())
//                .createdBy(beanConfig.getCurrentUserId())
                .build();
        return projectRepository.save(project);
    }

}
