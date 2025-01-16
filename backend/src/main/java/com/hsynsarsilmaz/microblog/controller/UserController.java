package com.hsynsarsilmaz.microblog.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsynsarsilmaz.microblog.dto.ApiResponse;
import com.hsynsarsilmaz.microblog.dto.UserProfile;
import com.hsynsarsilmaz.microblog.entity.User;
import com.hsynsarsilmaz.microblog.service.UserService;
import com.hsynsarsilmaz.microblog.service.Utils;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUserProfile(@PathVariable String username) {
        username = userService.validateAndSanitizeUsername(username);
        User user = userService.getUserByUsername(username);

        String message = messageSource.getMessage("user.retrieve.successful", null, Locale.getDefault());

        return Utils.successResponse(message, new UserProfile(user), HttpStatus.OK);

    }

}
