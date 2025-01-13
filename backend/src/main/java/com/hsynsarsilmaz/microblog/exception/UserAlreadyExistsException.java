package com.hsynsarsilmaz.microblog.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends MbException {

    public UserAlreadyExistsException() {
        super("user.already.exists", HttpStatus.CONFLICT);
    }

}