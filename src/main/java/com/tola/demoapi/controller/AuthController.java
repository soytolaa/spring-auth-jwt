package com.tola.demoapi.controller;

import java.time.LocalDateTime;
import java.io.UnsupportedEncodingException;
import jakarta.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tola.demoapi.model.request.UserRequest;
import com.tola.demoapi.model.request.ForgetRequest;
import com.tola.demoapi.model.request.UserLoginRequest;
import com.tola.demoapi.model.response.ApiResponse;
import com.tola.demoapi.service.AuthService;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.CrossOrigin;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Authentication", description = "Authentication and Authorization API for user registration, login, and password management")
public class AuthController {
        private final AuthService authService;

        @PostMapping("/register")
        @Operation(summary = "Register a new user with email, password, and name")
        public ResponseEntity<?> register(@RequestBody UserRequest userRequest)
                        throws MessagingException, UnsupportedEncodingException {
                ApiResponse<?> response = ApiResponse.builder()
                                .message("User registered successfully")
                                .payload(authService.register(userRequest))
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @PutMapping("/verify-otp")
        @Operation(summary = "Verify OTP with otpCode")
        public ResponseEntity<?> verifyOtp(@RequestParam(name = "otpCode") Integer otpCode) {
                ApiResponse<?> response = ApiResponse.builder()
                                .message("OTP verified successfully")
                                .payload(authService.verifyOtp(otpCode))
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        // resend otp
        @PutMapping("/resend-otp")
        @Operation(summary = "Resend OTP to user's email")
        public ResponseEntity<?> resendOtp(@RequestParam(name = "email") String email)
                        throws MessagingException, UnsupportedEncodingException {
                ApiResponse<?> response = ApiResponse.builder()
                                .message("OTP resent successfully")
                                .payload(authService.resendOtp(email))
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        // login
        @PostMapping("/login")
        @Operation(summary = "Login with email and password")
        public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest)
                        throws MessagingException, UnsupportedEncodingException {
                ApiResponse<?> response = ApiResponse.builder()
                                .message("Login successful")
                                .payload(authService.login(userLoginRequest))
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        // change password
        @SecurityRequirement(name = "bearerAuth")
        @PutMapping("/change-password")
        @Operation(summary = "Change password with new password")
        public ResponseEntity<?> changePassword(@RequestBody UserRequest userRequest) {
                ApiResponse<?> response = ApiResponse.builder()
                                .message("Password changed successfully")
                                .payload(authService.changePassword(userRequest))
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @SecurityRequirement(name = "bearerAuth")
        @GetMapping("/user-detail")
        @Operation(summary = "Get user details with email")
        public ResponseEntity<?> getUserDetails(@RequestParam(name = "email") String email) {
                ApiResponse<?> response = ApiResponse.builder()
                                .message("Get user detail successfully")
                                .payload(authService.getUserDetails(email))
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @PutMapping("/forgot-password")
        @Operation(summary = "Forgot password with email")
        public ResponseEntity<?> forgotPassword(@RequestBody ForgetRequest forgetRequest) {
                ApiResponse<?> response = ApiResponse.builder()
                                .message("Forgot password successfully")
                                .payload(authService.forgotPassword(forgetRequest))
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @GetMapping("/recovery-password")
        @Operation(summary = "Recovery password with email")
        public ResponseEntity<?> recovery(@RequestParam(name = "email") String email)
                        throws MessagingException, UnsupportedEncodingException {
                ApiResponse<?> response = ApiResponse.builder()
                                .message("Recovery successfully")
                                .payload(authService.recovery(email))
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .timestamp(LocalDateTime.now())
                                .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}
