package com.tola.demoapi.mapper;

import com.tola.demoapi.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import com.tola.demoapi.model.entities.Task;
import com.tola.demoapi.model.request.TaskRequest;
import com.tola.demoapi.model.response.TaskResponse;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, Collectors.class })
public interface TaskMapper {
    // @Mapping(target = "id", ignore = true)
    // Task toEntity(TaskRequest taskRequest);
    //
    // @Mapping(target = "projectId", expression = "java(task.getProject() != null
    // ? task.getProject().getId() : null)")
     TaskResponse toResponse(Task task, List<UserResponse> assignees, Long projectId);
    //
    // @Mapping(target = "project", ignore = true)
    // @Mapping(target = "assigner", ignore = true)
    // @Mapping(target = "assignees", ignore = true)
    // @Mapping(target = "assignedAt", ignore = true)
    // @Mapping(target = "dueAt", ignore = true)
    // @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    // @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    // void toUpdate(TaskRequest taskRequest, @MappingTarget Task task);
}
