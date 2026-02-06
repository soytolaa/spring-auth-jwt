package com.tola.demoapi.model.request;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthLoginRequest {
    private String provider;
    private String email;
    private String name;
    private String oauthId;
}
