package com.tola.demoapi.service;

import com.tola.demoapi.model.request.UserRequest;
import com.tola.demoapi.model.request.ForgetRequest;
import com.tola.demoapi.model.request.UserLoginRequest;
import com.tola.demoapi.model.response.TokenResponse;
import com.tola.demoapi.model.response.UserResponse;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AuthService {
    UserResponse register(UserRequest userRequest) throws MessagingException, UnsupportedEncodingException;

    UserResponse verifyOtp(Integer otpCode);

    Integer resendOtp(String email) throws MessagingException, UnsupportedEncodingException;

    TokenResponse login(UserLoginRequest userLoginRequest) throws MessagingException, UnsupportedEncodingException;

    UserResponse changePassword(UserRequest userRequest);

    UserResponse getUserDetails(String email);

    UserResponse forgotPassword(ForgetRequest forgetRequest);

    UserResponse recovery(String email) throws MessagingException, UnsupportedEncodingException;
}
