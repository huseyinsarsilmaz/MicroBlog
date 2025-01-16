package com.hsynsarsilmaz.microblog.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hsynsarsilmaz.microblog.entity.User;
import com.hsynsarsilmaz.microblog.service.UserService;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserService userService;

    public UserAuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);

        return new CustomUserDetails(user);
    }

}