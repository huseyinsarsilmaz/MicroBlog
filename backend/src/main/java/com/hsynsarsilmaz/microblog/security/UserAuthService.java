package com.hsynsarsilmaz.microblog.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hsynsarsilmaz.microblog.entity.User;
import com.hsynsarsilmaz.microblog.exception.UserNotFoundException;
import com.hsynsarsilmaz.microblog.repository.UserRepository;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        return new CustomUserDetails(optUser.get());
    }

}