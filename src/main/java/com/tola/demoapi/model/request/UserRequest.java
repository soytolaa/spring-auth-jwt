package com.tola.demoapi.model.request;

import lombok.*;
import com.tola.demoapi.model.enums.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String userName;
    private String email;
    private String password;
    private String confirmPassword;
    private Type type;
}
