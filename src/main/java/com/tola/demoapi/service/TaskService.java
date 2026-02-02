package com.tola.demoapi.service;

import com.tola.demoapi.model.request.TaskRequest;
import com.tola.demoapi.model.response.TaskResponse;
import com.tola.demoapi.model.enums.Status;
import com.tola.demoapi.model.enums.PriorityStatus;
import java.time.LocalDateTime;
import java.util.List;
import com.tola.demoapi.model.request.UserTaskRequest;
public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest);
    TaskResponse addUserToTask(UserTaskRequest userTaskRequest);
    TaskResponse removeUserFromTask(UserTaskRequest userTaskRequest);
    Boolean updateTaskStatus(Long id, Status status);
    Boolean updateTaskPriorityStatus(Long id, PriorityStatus priorityStatus);
    Boolean updateTaskDueDate(Long id, LocalDateTime dueDate);
    Boolean deleteTask(Long id);
    TaskResponse getTaskById(Long id);
    List<TaskResponse> getAllTasksByProjectId(Long projectId);
}
