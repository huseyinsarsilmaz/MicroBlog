package com.hsynsarsilmaz.microblog.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hsynsarsilmaz.microblog.dto.ApiResponse;

public class Utils {
    public static ResponseEntity<ApiResponse> successResponse(String message, Object data, HttpStatus status) {
        ApiResponse response = new ApiResponse(true, message, data);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ApiResponse> failResponse(String message, Object data, HttpStatus status) {
        ApiResponse response = new ApiResponse(false, message, data);
        return new ResponseEntity<>(response, status);
    }
}
