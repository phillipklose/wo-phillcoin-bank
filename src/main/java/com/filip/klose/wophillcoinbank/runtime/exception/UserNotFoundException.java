package com.filip.klose.wophillcoinbank.runtime.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("No account for that user found");
    }
}
