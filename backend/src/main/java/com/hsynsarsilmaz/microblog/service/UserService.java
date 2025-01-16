package com.hsynsarsilmaz.microblog.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hsynsarsilmaz.microblog.dto.RegisterRequest;
import com.hsynsarsilmaz.microblog.entity.User;
import com.hsynsarsilmaz.microblog.enums.UserRole;
import com.hsynsarsilmaz.microblog.exception.InvalidRequestArgumentException;
import com.hsynsarsilmaz.microblog.exception.UserAlreadyExistsException;
import com.hsynsarsilmaz.microblog.exception.UserNotFoundException;
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

    public User getUserByUsername(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        return optUser.get();
    }

    public String validateAndSanitizeUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidRequestArgumentException("username.required");
        }

        username = username.trim();

        if (username.length() < 2 || username.length() > 32) {
            throw new InvalidRequestArgumentException("username.size");
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new InvalidRequestArgumentException("username.invalid.characters");
        }

        return username;

    }

}
