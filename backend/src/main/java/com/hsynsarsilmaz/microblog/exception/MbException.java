package com.hsynsarsilmaz.microblog.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MbException extends RuntimeException {
    private final String messageKey;
    private final HttpStatus httpStatus;

    public MbException(String messageKey, HttpStatus httpStatus) {
        super();
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
    }
}