package com.hsynsarsilmaz.microblog.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestArgumentException extends MbException {

    public InvalidRequestArgumentException(String messageKey) {
        super(messageKey, HttpStatus.BAD_REQUEST);
    }

}