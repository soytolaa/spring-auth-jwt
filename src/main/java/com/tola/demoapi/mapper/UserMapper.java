package com.tola.demoapi.mapper;

import com.tola.demoapi.model.entities.UserProject;
import com.tola.demoapi.model.response.UserResponse;
import com.tola.demoapi.model.entities.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.tola.demoapi.model.request.UserRequest;
import com.tola.demoapi.model.enums.Role;
import java.time.LocalDateTime;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUsername())
                .type(String.valueOf(user.getType()))
                .isVerified(user.getIsVerified())
                .isActive(user.getIsActive())
                .build();
    }

    public List<UserResponse> toResponse(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public User toEntity(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .userName(userRequest.getUserName())
                .type(userRequest.getType())
                .isVerified(false)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
    }

    public UserResponse toResTask(User user, UserProject userProject) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUsername())
                .role(userProject.getRole())
                .build();
    }
}
