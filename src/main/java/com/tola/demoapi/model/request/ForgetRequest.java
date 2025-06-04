package com.tola.demoapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgetRequest {
    private String email;
    private String newPassword;
    private String confirmNewPassword;
}
