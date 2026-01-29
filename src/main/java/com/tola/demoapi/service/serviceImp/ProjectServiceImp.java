package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.config.BeanConfig;
import com.tola.demoapi.mapper.ProjectMapper;
import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.entities.UserProject;
import com.tola.demoapi.model.enums.Role;
import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.model.response.ProjectResponse;
import com.tola.demoapi.repository.ProjectRepository;
import com.tola.demoapi.repository.UserProjectRepository;
import com.tola.demoapi.repository.UserRepository;
import com.tola.demoapi.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.tola.demoapi.exception.BadRequestException;
import com.tola.demoapi.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final BeanConfig beanConfig;

    @Override
    public List<ProjectResponse> getAllProjectsByUser() {     
        return userProjectRepository.findByUserUserId(UUID.fromString("1f616643-7ca4-41a4-8bd3-ce41ea454689")).stream()
                .map(UserProject::getProject)
                .filter(project -> project.getIsActive()==true)
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        User user = userRepository.findById(UUID.fromString("1f616643-7ca4-41a4-8bd3-ce41ea454689")).orElseThrow(() -> new NotFoundException("User not found"));
        Project project = projectRepository.save(projectMapper.toEntity(projectRequest,user));
        userProjectRepository.save(UserProject.builder()
                        .project(project)
                        .role(Role.ADMIN)
                        .joinedAt(LocalDateTime.now())
                        .user(user)
                .build());

        return projectMapper.toResponse(project);   
    }

    @Override
    public ProjectResponse updateProject(UUID id, ProjectRequest projectRequest) {
        User user = userRepository.findById(UUID.fromString("1f616643-7ca4-41a4-8bd3-ce41ea454689")).orElseThrow(() -> new NotFoundException("User not found"));
        if (user.getRole().equals(Role.ADMIN)) {
            throw new BadRequestException("You are not authorized to update this project");
        }
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        projectMapper.toUpdate(projectRequest, project);

        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse addUserToProjectByEmail(UUID id, String email) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        if (user.getRole().equals(Role.ADMIN)) {
            throw new BadRequestException("You are not authorized to add user to this project");
        }
        userProjectRepository.save(UserProject.builder()
                .project(project)
                .role(Role.USER)
                .joinedAt(LocalDateTime.now())
                .user(user)
                .build());
        return projectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse deactivateProject(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        project.setIsActive(false);
        project.setUpdatedAt(LocalDateTime.now());
        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse activateProject(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        project.setIsActive(true);
        project.setUpdatedAt(LocalDateTime.now());
        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public Boolean deleteProject(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        projectRepository.delete(project);
        return true;
    }
}
