package com.hsynsarsilmaz.microblog.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hsynsarsilmaz.microblog.dto.RegisterRequest;
import com.hsynsarsilmaz.microblog.entity.User;
import com.hsynsarsilmaz.microblog.enums.UserRole;
import com.hsynsarsilmaz.microblog.exception.UserAlreadyExistsException;
import com.hsynsarsilmaz.microblog.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest req) {
        req.setPassword(passwordEncoder.encode(req.getPassword()));

        User newUser = User.builder()
                .username(req.getUsername())
                .password(req.getPassword())
                .roles(UserRole.USER.name())
                .build();

        return userRepository.save(newUser);
    }

    public void isUsernameTaken(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException();
        }
    }

}
