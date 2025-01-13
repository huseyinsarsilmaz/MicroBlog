package com.hsynsarsilmaz.microblog.exception;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hsynsarsilmaz.microblog.dto.ApiResponse;
import com.hsynsarsilmaz.microblog.service.Utils;

@RestControllerAdvice
public class GlobalExceptionHandler {

    MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MbException.class)
    public ResponseEntity<ApiResponse> handleMbException(MbException ex) {
        String message = messageSource.getMessage(ex.getMessageKey(), null, Locale.getDefault());
        return Utils.failResponse(message, ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = messageSource.getMessage("request.valid", null,
                Locale.getDefault());
        return Utils.failResponse(message, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String message = messageSource.getMessage("validation.failed", null, Locale.getDefault());

        return Utils.failResponse(message, errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException ex) {
        String message = messageSource.getMessage("user.login.failed", null,
                Locale.getDefault());
        return Utils.failResponse(message, ex.getMessage(), HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
        String message = messageSource.getMessage("general.error", null, Locale.getDefault());
        return Utils.failResponse(message, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}