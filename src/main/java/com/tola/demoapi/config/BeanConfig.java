package com.tola.demoapi.config;

import com.tola.demoapi.model.entities.User;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.context.Context;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    private final JavaMailSender javaMailSender;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setCacheable(false);
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    MimeMessage message() {
        return javaMailSender.createMimeMessage();
    }

    @Bean
    public Context context() {
        return new Context();
    }

    @Bean
    public ModelAndView modelAndView() {
        return new ModelAndView();
    }

//    @Bean
//    public UUID getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof User user) {
//            return user.getUserId();
//        }
//        return null;
//    }

}