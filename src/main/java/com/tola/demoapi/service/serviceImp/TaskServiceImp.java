package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.config.BeanConfig;
import com.tola.demoapi.model.entities.Task;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.entities.UserTask;
import com.tola.demoapi.model.request.TaskRequest;
import com.tola.demoapi.model.response.TaskResponse;
import com.tola.demoapi.model.response.UserResponse;
import com.tola.demoapi.repository.*;
import com.tola.demoapi.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tola.demoapi.mapper.TaskMapper;
import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.UserProject;
import com.tola.demoapi.exception.NotFoundException;
import com.tola.demoapi.exception.BadRequestException;
import com.tola.demoapi.model.enums.Status;
import com.tola.demoapi.model.enums.PriorityStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.tola.demoapi.model.request.UserTaskRequest;
import com.tola.demoapi.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserTaskRepository userTaskRepository;
    private final UserProjectRepository userProjectRepository;
    private final BeanConfig beanConfig;
    private final UserMapper userMapper;

    public Long getUserId() {
        return beanConfig.getCurrentUserId().orElse(null);
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        Long currentUserId = getUserId();
        Project project = projectRepository.findById(taskRequest.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project not found"));

        List<User> assignees = userRepository.findAllById(taskRequest.getAssignees());

        if (assignees.size() != taskRequest.getAssignees().size()) {
            Set<Long> foundIds = assignees.stream().map(User::getUserId).collect(Collectors.toSet());
            List<Long> missingIds = taskRequest.getAssignees().stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new NotFoundException("User ids not found: " + missingIds);
        }

        User assigner = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Task task = taskRepository.save(taskRequest.toEntity(project, currentUserId, assignees));
        return task.taskResponse(assigner, assignees);
    }


    @Override
    public TaskResponse addUserToTask(UserTaskRequest userTaskRequest) {
        // Task task =
        // taskRepository.findById(userTaskRequest.getTaskId()).orElseThrow(() -> new
        // NotFoundException("Task not found"));
        //
        // // Get the project from the task to ensure consistency
        // Project project = task.getProject();
        // if (project == null) {
        // throw new NotFoundException("Project not found for this task");
        // }
        //
        // // If specific users provided, validate and add them
        // if (userTaskRequest.getUserIds() != null &&
        // !userTaskRequest.getUserIds().isEmpty()) {
        // for (Long userId : userTaskRequest.getUserIds()) {
        // // Check if user exists
        // User user = userRepository.findById(userId).orElseThrow(() -> new
        // NotFoundException("User not found with id: " + userId));
        //
        // // Check if user is a member of the project
        // if (!userProjectRepository.existsByUserUserIdAndProjectId(userId,
        // project.getId())) {
        // throw new BadRequestException("User with id " + userId + " (" +
        // user.getEmail() + ") is not a member of this project");
        // }
        //
        // // Check if user is already assigned to this task
        // if (userTaskRepository.findByUserUserIdAndTaskId(userId,
        // task.getId()).isPresent()) {
        // throw new BadRequestException("User with id " + userId + " (" +
        // user.getEmail() + ") is already assigned to this task");
        // }
        //
        // // Add user to task
        // userTaskRepository.save(UserTask.builder().user(user).task(task).build());
        // }
        // } else {
        // // If no specific users provided, add all project members who aren't already
        // // assigned
        // List<UserProject> userProjects =
        // userProjectRepository.findByProjectId(project.getId());
        //
        // userProjects.forEach(userProject -> {
        // // Check if user is already assigned
        // if
        // (userTaskRepository.findByUserUserIdAndTaskId(userProject.getUser().getUserId(),
        // task.getId()).isEmpty()) {
        // userTaskRepository.save(UserTask.builder().user(userProject.getUser()).task(task).build());
        // }
        // });
        // }
        //
        // return taskMapper.toResponse(task, new ArrayList<>(), project.getId());
        return null;
    }

    @Override
    public TaskResponse removeUserFromTask(UserTaskRequest userTaskRequest) {
        // Task task =
        // taskRepository.findById(userTaskRequest.getTaskId()).orElseThrow(() -> new
        // NotFoundException("Task not found"));
        // userTaskRequest.getUserIds().forEach(userId -> {
        // userTaskRepository.delete(userTaskRepository.findByUserUserIdAndTaskId(userId,
        // userTaskRequest.getTaskId()).orElseThrow(() -> new NotFoundException("User
        // task not found")));
        // });
        // return taskMapper.toResponse(task, new ArrayList<>(),
        // task.getProject().getId());
        return null;
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
    public Boolean updateTaskDueDate(Long id, LocalDate dueDate) {
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
        List<Task> tasks = taskRepository.findAllByProjectIdWithUsers(projectId);
        System.err.println("###=========>"+tasks);
        return null;
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        // Task task = taskRepository.findById(id).orElseThrow(() -> new
        // NotFoundException("Task not found"));
        // List<UserResponse> assignees = new ArrayList<>();
        // task.getUserTasks().forEach(userTask -> {
        // User user = userTask.getUser();
        // assignees.add(UserResponse.builder().userId(user.getUserId()).email(user.getEmail()).userName(user.getUsername()).type(String.valueOf(user.getType())).isVerified(user.getIsVerified()).isActive(user.getIsActive()).build());
        // });
        // return taskMapper.toResponse(task, assignees, task.getProject().getId());
        return null;
    }
}
