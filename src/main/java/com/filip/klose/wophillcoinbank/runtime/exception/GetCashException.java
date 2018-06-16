package com.filip.klose.wophillcoinbank.runtime.exception;

public class GetCashException extends Exception {

    public GetCashException() {
        super("The amount of cash is too big to get");
    }
}
