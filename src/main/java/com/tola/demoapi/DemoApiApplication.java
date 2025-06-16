package com.tola.demoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;

@OpenAPIDefinition(info = @Info(title = "Authentication and Authorization API", version = "v1", description = "Using JWT and Spring Security, Verification Email with OTP, Credentials, Google and Github login."))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", in = SecuritySchemeIn.HEADER)
@SpringBootApplication
public class DemoApiApplication {
        public static void main(String[] args) {
                SpringApplication.run(DemoApiApplication.class, args);
        }

}
