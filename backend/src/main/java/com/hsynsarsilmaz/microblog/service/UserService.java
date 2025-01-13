package com.hsynsarsilmaz.microblog.service;

import com.hsynsarsilmaz.microblog.dto.RegisterRequest;
import com.hsynsarsilmaz.microblog.entity.User;
import com.hsynsarsilmaz.microblog.enums.UserRole;
import com.hsynsarsilmaz.microblog.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterRequest req) {
        req.setPassword(req.getPassword());
        User newUser = User.builder()
                .username(req.getUsername())
                .password(req.getPassword())
                .roles(UserRole.USER.name())
                .build();
        return userRepository.save(newUser);
    }
}
