package com.filip.klose.wophillcoinbank.runtime.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserUnauthorizedException extends RuntimeException {

    public UserUnauthorizedException() {
        super("You have entered an invalid username or password");
    }
}
