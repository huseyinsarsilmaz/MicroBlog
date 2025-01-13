package com.hsynsarsilmaz.microblog.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hsynsarsilmaz.microblog.dto.ApiResponse;
import com.hsynsarsilmaz.microblog.dto.LoginRequest;
import com.hsynsarsilmaz.microblog.dto.RegisterRequest;
import com.hsynsarsilmaz.microblog.dto.RegisterResponse;
import com.hsynsarsilmaz.microblog.entity.User;
import com.hsynsarsilmaz.microblog.security.JwtService;
import com.hsynsarsilmaz.microblog.service.UserService;
import com.hsynsarsilmaz.microblog.service.Utils;

import jakarta.validation.Valid;

@RestController
public class AuthController {
    private final MessageSource messageSource;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;

    public AuthController(MessageSource messageSource, AuthenticationManager authenticationManager,
            JwtService jwtService, UserService userService) {
        this.messageSource = messageSource;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    private String getJwtToken(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        return jwtService.generateToken(req.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest req) {
        String token = getJwtToken(req);
        String message = messageSource.getMessage("user.login.successful", null, Locale.getDefault());

        return Utils.successResponse(message, token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody RegisterRequest req) {
        userService.isUsernameTaken(req.getUsername());

        User savedUser = userService.registerUser(req);
        String message = messageSource.getMessage("user.register.successful", null, Locale.getDefault());
        return Utils.successResponse(message, new RegisterResponse(savedUser), HttpStatus.CREATED);
    }

}
