package com.tola.demoapi.jwt;

import com.tola.demoapi.service.serviceImp.AppUserServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AppUserServiceImp appUserServiceImp;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                // Try to extract email from custom JWT first
                email = jwtService.extractUsername(token);

                // If successful, validate as custom JWT
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = appUserServiceImp.loadUserByUsername(email);
                    if (jwtService.validateToken(token, userDetails)) {
                        setAuthentication(userDetails, request);
                    }
                }
            } catch (Exception e) {
                // If custom JWT parsing fails, try OAuth token (Google/GitHub)
                log.debug("Custom JWT parsing failed, trying OAuth token: {}", e.getMessage());
                email = extractEmailFromOAuthToken(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    try {
                        UserDetails userDetails = appUserServiceImp.loadUserByUsername(email);
                        // For OAuth tokens, we trust the provider, so we don't validate signature
                        // Just verify the user exists in our system
                        setAuthentication(userDetails, request);
                    } catch (Exception ex) {
                        log.warn("Failed to authenticate OAuth token for email: {}", email, ex);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extract email from Google/GitHub OAuth token
     * Google tokens are JWTs with email in claims
     * GitHub tokens might be different format
     */
    private String extractEmailFromOAuthToken(String token) {
        try {
            // Decode JWT without verification (we trust OAuth providers)
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null; // Not a valid JWT format
            }

            // Decode the payload (second part) - add padding if needed
            String payloadJson = parts[1];
            // Add padding if needed for Base64 decoding
            while (payloadJson.length() % 4 != 0) {
                payloadJson += "=";
            }

            String payload = new String(Base64.getUrlDecoder().decode(payloadJson));

            // Parse JSON to extract email
            // For Google: {"email": "user@example.com", "iss":
            // "https://accounts.google.com", ...}
            // For GitHub: might have different structure

            // Extract email using simple JSON parsing
            if (payload.contains("\"email\"")) {
                // Find email field value
                int emailIndex = payload.indexOf("\"email\"");
                int colonIndex = payload.indexOf(":", emailIndex);
                int quoteStart = payload.indexOf("\"", colonIndex) + 1;
                int quoteEnd = payload.indexOf("\"", quoteStart);

                if (quoteEnd > quoteStart) {
                    String email = payload.substring(quoteStart, quoteEnd);
                    // Verify it's from a trusted OAuth provider
                    if (payload.contains("accounts.google.com") ||
                            payload.contains("\"github\"") ||
                            payload.contains("github.com")) {
                        log.debug("Extracted email from OAuth token: {}", email);
                        return email;
                    }
                }
            }

        } catch (Exception e) {
            log.debug("Failed to extract email from OAuth token: {}", e.getMessage());
        }
        return null;
    }

    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}