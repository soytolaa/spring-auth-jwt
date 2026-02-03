package com.tola.demoapi.model.response;

import java.time.LocalDateTime;

import com.tola.demoapi.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String userName;
    private String email;
    private String type;
    private Boolean isVerified;
    private Boolean isActive;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
