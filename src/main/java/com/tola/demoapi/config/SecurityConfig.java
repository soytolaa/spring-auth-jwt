package com.tola.demoapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import com.tola.demoapi.jwt.JwtAuthEntrypoint;
import com.tola.demoapi.jwt.JwtAuthFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
        private final JwtAuthFilter jwtAuthFilter;
        private final JwtAuthEntrypoint jwtAuthEntrypoint;

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(withDefaults()).csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(request -> request
                                                .requestMatchers("/api/v1/auth/**",
                                                                "/v3/api-docs/**",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/api/v1/products/**",
                                                                "/api/v1/categories/**",
                                                                "/api/v1/stocks/**",
                                                                "/api/v1/purchases/**")
                                                .permitAll()
                                                // .requestMatchers("/api/v1/products/**", "/api/v1/categories/**",
                                                // "/api/v1/stocks/**", "/api/v1/purchases/**")
                                                // .authenticated()
                                                .anyRequest().authenticated())
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntrypoint))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}
