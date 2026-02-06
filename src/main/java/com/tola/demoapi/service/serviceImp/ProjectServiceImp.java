package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.config.BeanConfig;
import com.tola.demoapi.mapper.ProjectMapper;
import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.entities.UserProject;
import com.tola.demoapi.model.enums.Role;
import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.model.response.ProjectResponse;
import com.tola.demoapi.model.response.UserResponse;
import com.tola.demoapi.repository.ProjectRepository;
import com.tola.demoapi.repository.UserProjectRepository;
import com.tola.demoapi.repository.UserRepository;
import com.tola.demoapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.tola.demoapi.exception.BadRequestException;
import com.tola.demoapi.exception.NotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final BeanConfig beanConfig;
    private final Logger logger = LoggerFactory.getLogger(ProjectServiceImp.class);

    public Long getCurrentUserId() {
        return beanConfig.getCurrentUserId().orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public List<ProjectResponse> getAllProjectsByUser() {
        List<ProjectResponse> projectResponses = new ArrayList<>();
        List<Project> projects = projectRepository.findAllByUserId(getCurrentUserId());
        projects.forEach(project -> {
            Integer numberCount = userProjectRepository.countMember(project.getId());
            projectResponses.add(ProjectResponse.builder().membersCount(numberCount).id(project.getId())
                    .name(project.getName()).description(project.getDescription()).createdAt(project.getCreatedAt())
                    .updatedAt(project.getUpdatedAt()).createdBy(project.getCreatedBy().getUserId())
                    .code(project.getCode()).build());
        });
        return projectResponses;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        User user = userRepository.findById(getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Project project = projectRepository.save(projectMapper.toEntity(projectRequest, user));
        userProjectRepository.save(UserProject.builder()
                .project(project)
                .role(Role.ADMIN)
                .joinedAt(LocalDateTime.now())
                .user(user)
                .build());

        return projectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        User user = userRepository.findById(getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (user.getRole().equals(Role.ADMIN)) {
            throw new BadRequestException("You are not authorized to update this project");
        }
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        projectMapper.toUpdate(projectRequest, project);

        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse addUserToProjectByEmail(Long id, String email) {
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
    public ProjectResponse activeAndDeactiveProject(Long id, Boolean isActive) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        project.setIsActive(isActive);
        project.setUpdatedAt(LocalDateTime.now());
        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public Boolean deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
        projectRepository.delete(project);
        return true;
    }

    @Override
    public List<UserResponse> getUserInProject(Long id) {
        List<UserProject> userProjects = userProjectRepository.findByProjectId(id);

        return userProjects.stream()
                .map(userProject -> {
                    User user = userProject.getUser();
                    return UserResponse.builder()
                            .userId(user.getUserId())
                            .email(user.getEmail())
                            .userName(user.getUsername())
                            .type(String.valueOf(user.getType()))
                            .isVerified(user.getIsVerified())
                            .isActive(user.getIsActive())
                            .role(userProject.getRole()) // Get role from UserProject
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Boolean joinProjectByCode(UUID code) {
        Project project = projectRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Project not found"));
        User user = userRepository.findById(getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        userProjectRepository.save(UserProject.builder()
                .project(project)
                .role(Role.ADMIN)
                .joinedAt(LocalDateTime.now())
                .user(user)
                .build());
        return true;
    }

}
