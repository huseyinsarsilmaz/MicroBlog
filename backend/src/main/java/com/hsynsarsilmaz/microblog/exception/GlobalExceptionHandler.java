package com.hsynsarsilmaz.microblog.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> handleAdaFoodException(MbException ex) {
        String message = messageSource.getMessage(ex.getMessageKey(), null, Locale.getDefault());
        return Utils.failResponse(message, ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
        String message = messageSource.getMessage("general.error", null, Locale.getDefault());
        return Utils.failResponse(message, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}