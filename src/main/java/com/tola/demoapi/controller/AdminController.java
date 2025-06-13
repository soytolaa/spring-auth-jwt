package com.tola.demoapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Admin", description = "Admin API for user management")
@SecurityRequirement(name = "bearerAuth")
@RolesAllowed("ROLE_ADMIN")
public class AdminController {

}
