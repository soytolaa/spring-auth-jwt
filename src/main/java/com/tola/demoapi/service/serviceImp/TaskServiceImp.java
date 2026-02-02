package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.model.entities.Task;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.entities.UserTask;
import com.tola.demoapi.model.request.TaskRequest;
import com.tola.demoapi.model.response.TaskResponse;
import com.tola.demoapi.model.response.UserResponse;
import com.tola.demoapi.repository.*;
import com.tola.demoapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tola.demoapi.mapper.TaskMapper;
import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.UserProject;
import com.tola.demoapi.exception.NotFoundException;
import com.tola.demoapi.exception.BadRequestException;
import com.tola.demoapi.model.enums.Status;
import com.tola.demoapi.model.enums.PriorityStatus;

import java.util.ArrayList;
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

        // Validate that all assignees are members of the project
        if (taskRequest.getAssignees() != null && !taskRequest.getAssignees().isEmpty()) {
            for (Long assigneeId : taskRequest.getAssignees()) {
                // Check if user exists
                User user = userRepository.findById(assigneeId)
                        .orElseThrow(() -> new NotFoundException("User not found with id: " + assigneeId));

                // Check if user is a member of the project
                if (!userProjectRepository.existsByUserUserIdAndProjectId(assigneeId, project.getId())) {
                    throw new BadRequestException(
                            "User with id " + assigneeId + " (" + user.getEmail()
                                    + ") is not a member of this project");
                }
            }
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

        // Assign users to task (all validated to be project members)
        if (taskRequest.getAssignees() != null && !taskRequest.getAssignees().isEmpty()) {
            taskRequest.getAssignees().forEach(assigneeId -> {
                User user = userRepository.findById(assigneeId)
                        .orElseThrow(() -> new NotFoundException("User not found with id: " + assigneeId));
                userTaskRepository.save(UserTask.builder()
                        .user(user)
                        .task(savedTask)
                        .build());
            });
        }

        // Get assignees from saved task
        List<Long> assigneeIds = userTaskRepository.findByTaskId(savedTask.getId()).stream()
                .map(userTask -> userTask.getUser().getUserId())
                .collect(Collectors.toList());
        List<UserResponse> assignees = new ArrayList<>();
        savedTask.getUserTasks().forEach(userTask -> {
            User user = userTask.getUser();
            assignees.add(UserResponse.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .userName(user.getUsername())
                    .type(String.valueOf(user.getType()))
                    .isVerified(user.getIsVerified())
                    .isActive(user.getIsActive())
                    .build());
        });
        return taskMapper.toResponse(savedTask, assignees, project.getId());
    }

    @Override
    public TaskResponse addUserToTask(UserTaskRequest userTaskRequest) {
        Task task = taskRepository.findById(userTaskRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found"));

        // Get the project from the task to ensure consistency
        Project project = task.getProject();
        if (project == null) {
            throw new NotFoundException("Project not found for this task");
        }

        // If specific users provided, validate and add them
        if (userTaskRequest.getUserIds() != null && !userTaskRequest.getUserIds().isEmpty()) {
            for (Long userId : userTaskRequest.getUserIds()) {
                // Check if user exists
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

                // Check if user is a member of the project
                if (!userProjectRepository.existsByUserUserIdAndProjectId(userId, project.getId())) {
                    throw new BadRequestException(
                            "User with id " + userId + " (" + user.getEmail() + ") is not a member of this project");
                }

                // Check if user is already assigned to this task
                if (userTaskRepository.findByUserUserIdAndTaskId(userId, task.getId()).isPresent()) {
                    throw new BadRequestException(
                            "User with id " + userId + " (" + user.getEmail() + ") is already assigned to this task");
                }

                // Add user to task
                userTaskRepository.save(UserTask.builder()
                        .user(user)
                        .task(task)
                        .build());
            }
        } else {
            // If no specific users provided, add all project members who aren't already
            // assigned
            List<UserProject> userProjects = userProjectRepository.findByProjectId(project.getId())
                    .orElseThrow(() -> new NotFoundException("No users found in this project"));

            userProjects.forEach(userProject -> {
                // Check if user is already assigned
                if (userTaskRepository.findByUserUserIdAndTaskId(
                        userProject.getUser().getUserId(), task.getId()).isEmpty()) {
                    userTaskRepository.save(UserTask.builder()
                            .user(userProject.getUser())
                            .task(task)
                            .build());
                }
            });
        }

        return taskMapper.toResponse(task, new ArrayList<>(),
                project.getId());
    }

    @Override
    public TaskResponse removeUserFromTask(UserTaskRequest userTaskRequest) {
        Task task = taskRepository.findById(userTaskRequest.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found"));
        userTaskRequest.getUserIds().forEach(userId -> {
            userTaskRepository.delete(userTaskRepository.findByUserUserIdAndTaskId(userId, userTaskRequest.getTaskId())
                    .orElseThrow(() -> new NotFoundException("User task not found")));
        });
        return taskMapper.toResponse(task, new ArrayList<>(), task.getProject().getId());
    }

    @Override
    public Boolean updateTaskStatus(Long id, Status status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setStatus(status);
        return true;
    }

    @Override
    public Boolean updateTaskPriorityStatus(Long id, PriorityStatus priorityStatus) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setPriorityStatus(priorityStatus);
        return true;
    }

    @Override
    public Boolean updateTaskDueDate(Long id, LocalDateTime dueDate) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setDueAt(dueDate);
        return true;
    }

    @Override
    public Boolean deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        taskRepository.delete(task);
        return true;
    }

    @Override
    public List<TaskResponse> getAllTasksByProjectId(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId)
                .orElseThrow(() -> new NotFoundException("Tasks not found"));

        return tasks.stream()
                .map(task -> {
                    // Get assignees for this specific task only (no duplicates)
                    List<UserResponse> assignees = new ArrayList<>();
                    task.getUserTasks().forEach(userTask -> {
                        User user = userTask.getUser();
                        assignees.add(UserResponse.builder()
                                .userId(user.getUserId())
                                .email(user.getEmail())
                                .userName(user.getUsername())
                                .type(String.valueOf(user.getType()))
                                .isVerified(user.getIsVerified())
                                .isActive(user.getIsActive())
                                .build());
                    });
                    return taskMapper.toResponse(task, assignees, task.getProject().getId());
                })
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        List<UserResponse> assignees = new ArrayList<>();
        task.getUserTasks().forEach(userTask -> {
            User user = userTask.getUser();
            assignees.add(UserResponse.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .userName(user.getUsername())
                    .type(String.valueOf(user.getType()))
                    .isVerified(user.getIsVerified())
                    .isActive(user.getIsActive())
                    .build());
        });
        return taskMapper.toResponse(task, assignees, task.getProject().getId());
    }
}
