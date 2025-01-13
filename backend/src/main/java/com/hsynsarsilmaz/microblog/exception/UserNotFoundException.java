package com.hsynsarsilmaz.microblog.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends MbException {

    public UserNotFoundException() {
        super("user.not.found", HttpStatus.NOT_FOUND);
    }

}
