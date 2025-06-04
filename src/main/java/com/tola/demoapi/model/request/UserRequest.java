package com.tola.demoapi.model.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String userName;
    private String email;
    private String password;
    private String confirmPassword;
}
