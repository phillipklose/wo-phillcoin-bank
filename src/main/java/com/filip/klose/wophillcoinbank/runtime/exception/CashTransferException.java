package com.filip.klose.wophillcoinbank.runtime.exception;

public class CashTransferException extends Exception {

    public CashTransferException() {
        super("Could not transfer cash between accounts");
    }
}
