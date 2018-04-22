package com.filip.klose.wophillcoinbank.runtime.exception;

public class ConfigurationNotFoundException extends Exception {

    public ConfigurationNotFoundException() {
        super("Configuration not found");
    }
}
