package com.tola.demoapi.mapper;

import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.User;
import com.tola.demoapi.model.request.ProjectRequest;
import com.tola.demoapi.model.response.ProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring", imports = { LocalDateTime.class })
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "createdBy", source = "user")
    @Mapping(target = "isActive", expression = "java(true)")
    @Mapping(target = "tasks", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "userProjects", expression = "java(new java.util.ArrayList<>())")
    Project toEntity(ProjectRequest projectRequest, User user);

    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    void toUpdate(ProjectRequest projectRequest, @MappingTarget Project project);

    @Mapping(target = "createdBy", expression = "java(project.getCreatedBy().getUserId())")
    ProjectResponse toResponse(Project project);

}
