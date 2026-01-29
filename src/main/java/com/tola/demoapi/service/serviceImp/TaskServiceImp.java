package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.model.entities.Task;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.entities.UserTask;
import com.tola.demoapi.model.request.TaskRequest;
import com.tola.demoapi.model.response.TaskResponse;
import com.tola.demoapi.repository.*;
import com.tola.demoapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tola.demoapi.mapper.TaskMapper;
import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.UserProject;
import com.tola.demoapi.exception.NotFoundException;
import com.tola.demoapi.model.enums.Status;
import com.tola.demoapi.model.enums.PriorityStatus;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import com.tola.demoapi.model.request.UserTaskRequest;
@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserTaskRepository userTaskRepository;
    private final UserProjectRepository userProjectRepository;

    public User getUser() {
        return userRepository.findById(1L).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        Project project = projectRepository.findById(taskRequest.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));
        if (!taskRequest.getAssignees().isEmpty()) {
            taskRequest.getAssignees().forEach(assignee -> {
                userRepository.findById(assignee).orElseThrow(() -> new NotFoundException("User not found"));
            });
        }
        Task task = Task.builder()
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .status(taskRequest.getStatus())
                .priorityStatus(taskRequest.getPriorityStatus())
                .project(project)
                .assigner(getUser().getUserId())
                .assignedAt(LocalDateTime.now())
                .dueAt(taskRequest.getDueAt())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Task savedTask = taskRepository.save(task);
        if (!taskRequest.getAssignees().isEmpty()) {
            taskRequest.getAssignees().forEach(assignee -> {
                userTaskRepository.save(UserTask.builder()
                        .user(userRepository.findById(assignee)
                                .orElseThrow(() -> new NotFoundException("User not found")))
                        .task(savedTask)
                        .build());
            });
        }
        return taskMapper.toResponse(savedTask,
                taskRequest.getAssignees().stream().map(Long::valueOf).collect(Collectors.toList()), project.getId());
    }

    @Override
    public TaskResponse addUserToTask(UserTaskRequest userTaskRequest) {
        Project project = projectRepository.findById(userTaskRequest.getProjectId()).orElseThrow(() -> new NotFoundException("Project not found"));
        List<UserProject> userProjects = userProjectRepository.findByProjectId(userTaskRequest.getProjectId()).orElseThrow(() -> new NotFoundException("User not found"));
        Task task = taskRepository.findById(userTaskRequest.getTaskId()).orElseThrow(() -> new NotFoundException("Task not found"));
        userProjects.forEach(userProject -> {
            userTaskRepository.save(UserTask.builder()
                    .user(userProject.getUser())
                    .task(task)
                    .build());
        });
        return taskMapper.toResponse(task, userTaskRepository.findByTaskId(userTaskRequest.getTaskId()).stream()
                .map(userTask -> userTask.getUser().getUserId()).collect(Collectors.toList()), userTaskRequest.getProjectId());
    }

    @Override
    public TaskResponse removeUserFromTask(UserTaskRequest userTaskRequest) {
        Task task = taskRepository.findById(userTaskRequest.getTaskId()).orElseThrow(() -> new NotFoundException("Task not found"));
        userTaskRequest.getUserIds().forEach(userId -> {
            userTaskRepository.delete(userTaskRepository.findByUserUserIdAndTaskId(userId, userTaskRequest.getTaskId())
                    .orElseThrow(() -> new NotFoundException("User task not found")));
        });
        return taskMapper.toResponse(task, userTaskRequest.getUserIds(), task.getProject().getId());
    }

    @Override
    public TaskResponse updateTaskStatus(Long id, Status status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setStatus(status);
        return taskMapper.toResponse(
                taskRepository.save(task), userTaskRepository.findByTaskId(id).stream()
                        .map(userTask -> userTask.getUser().getUserId()).collect(Collectors.toList()),
                task.getProject().getId());
    }

    @Override
    public TaskResponse updateTaskPriorityStatus(Long id, PriorityStatus priorityStatus) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setPriorityStatus(priorityStatus);
        return taskMapper.toResponse(
                taskRepository.save(task), userTaskRepository.findByTaskId(id).stream()
                        .map(userTask -> userTask.getUser().getUserId()).collect(Collectors.toList()),
                task.getProject().getId());
    }

    @Override
    public TaskResponse updateTaskDueDate(Long id, LocalDateTime dueDate) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setDueAt(dueDate);
        return taskMapper.toResponse(
                taskRepository.save(task), userTaskRepository.findByTaskId(id).stream()
                        .map(userTask -> userTask.getUser().getUserId()).collect(Collectors.toList()),
                task.getProject().getId());
    }

    @Override
    public Boolean deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        taskRepository.delete(task);
        return true;
    }
}
