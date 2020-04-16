package com.jaybe.pushnotifierdemo.exceptions;

public class NotValidFcmConfigFileException extends Exception {

    public NotValidFcmConfigFileException(String message) {
        super(message);
    }

    public NotValidFcmConfigFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
