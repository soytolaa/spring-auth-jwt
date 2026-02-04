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
    private final AppUserServiceImp authService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            // Check if it's an OAuth token (Google/GitHub) or custom JWT
            if (isOAuthToken(token)) {
                // Handle OAuth token (Google/GitHub)
                email = extractEmailFromOAuthToken(token);
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    try {
                        UserDetails userDetails = authService.loadUserByUsername(email);
                        // For OAuth tokens, we trust the provider - just verify user exists
                        setAuthentication(userDetails, request);
                    } catch (Exception e) {
                        log.warn("Failed to authenticate OAuth token for email: {}", email, e);
                    }
                }
            } else {
                // Handle custom JWT token
                try {
                    email = jwtService.extractUsername(token);
                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = authService.loadUserByUsername(email);
                        if (jwtService.validateToken(token, userDetails)) {
                            setAuthentication(userDetails, request);
                        }
                    }
                } catch (Exception e) {
                    log.debug("Failed to parse custom JWT token: {}", e.getMessage());
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Check if token is an OAuth token (Google/GitHub) by examining the header
     */
    private boolean isOAuthToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false; // Not a valid JWT format
            }

            // Decode header to check algorithm
            String headerJson = decodeBase64Url(parts[0]);
            // OAuth tokens use RS256, custom tokens use HS256
            return headerJson.contains("\"alg\":\"RS256\"") ||
                    headerJson.contains("\"alg\":\"ES256\"") ||
                    headerJson.contains("accounts.google.com") ||
                    headerJson.contains("github");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extract email from Google/GitHub OAuth token
     */
    private String extractEmailFromOAuthToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null;
            }

            // Decode payload (second part)
            String payloadJson = decodeBase64Url(parts[1]);

            // Extract email from JSON payload
            if (payloadJson.contains("\"email\"")) {
                int emailIndex = payloadJson.indexOf("\"email\"");
                int colonIndex = payloadJson.indexOf(":", emailIndex);
                int quoteStart = payloadJson.indexOf("\"", colonIndex) + 1;
                int quoteEnd = payloadJson.indexOf("\"", quoteStart);

                if (quoteEnd > quoteStart) {
                    String email = payloadJson.substring(quoteStart, quoteEnd);
                    // Verify it's from a trusted OAuth provider
                    if (payloadJson.contains("accounts.google.com") ||
                            payloadJson.contains("\"github\"") ||
                            payloadJson.contains("github.com")) {
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

    /**
     * Decode Base64URL string (used in JWT)
     */
    private String decodeBase64Url(String base64Url) {
        // Add padding if needed
        String base64 = base64Url.replace('-', '+').replace('_', '/');
        while (base64.length() % 4 != 0) {
            base64 += "=";
        }
        return new String(Base64.getDecoder().decode(base64));
    }

    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}