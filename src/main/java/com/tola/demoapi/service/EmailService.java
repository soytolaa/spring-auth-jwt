package com.tola.demoapi.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendEmail(String email, String otp) throws MessagingException, UnsupportedEncodingException;
}
