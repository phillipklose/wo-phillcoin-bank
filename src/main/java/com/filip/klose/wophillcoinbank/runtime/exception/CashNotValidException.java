package com.filip.klose.wophillcoinbank.runtime.exception;

public class CashNotValidException extends Exception {

    public CashNotValidException() {
        super("The cash is not registered in PhillCoinBank!");
    }
}
