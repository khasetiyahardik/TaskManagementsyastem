package com.example.TMS.exception;

public class CredentialsAreWrongException extends RuntimeException {
    public CredentialsAreWrongException(String message) {
        super(message);
    }
}
