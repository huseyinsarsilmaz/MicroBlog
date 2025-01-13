package com.hsynsarsilmaz.microblog.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsynsarsilmaz.microblog.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private JwtAuthFilter authFilter;
    PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtAuthFilter authFilter, PasswordEncoder passwordEncoder) {
        this.authFilter = authFilter;
        this.passwordEncoder = passwordEncoder;
    }

    private void handleExceptionInFilterChain(HttpServletRequest request, HttpServletResponse response,
            RuntimeException exception, int responseCode) throws JsonProcessingException, IOException {
        ApiResponse apiResponse = new ApiResponse(false,
                "Unauthorized: " + exception.getMessage(), null);
        response.setStatus(responseCode);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            handleExceptionInFilterChain(request, response, authException,
                                    HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            handleExceptionInFilterChain(request, response, accessDeniedException,
                                    HttpServletResponse.SC_FORBIDDEN);
                        }))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}