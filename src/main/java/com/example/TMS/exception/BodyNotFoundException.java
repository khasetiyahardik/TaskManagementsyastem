package com.example.TMS.exception;

public class BodyNotFoundException extends RuntimeException {
    public BodyNotFoundException(String message) {
        super(message);
    }
}
