package com.hsynsarsilmaz.microblog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsynsarsilmaz.microblog.dto.ApiResponse;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserAuthService userAuthService;

    public JwtAuthFilter(JwtService jwtService, UserAuthService userAuthService) {
        this.jwtService = jwtService;
        this.userAuthService = userAuthService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        try {

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userAuthService.loadUserByUsername(username);

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            handleException(response, ex);
        }

    }

    private void handleException(HttpServletResponse response, Exception ex) throws IOException {
        ApiResponse apiResponse = new ApiResponse(false, "Unauthorized: " + ex.getMessage(), null);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }

}