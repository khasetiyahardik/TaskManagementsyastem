package com.example.TMS.exception;

public class DeveloperNameAlreadyTakenException extends RuntimeException{
    public DeveloperNameAlreadyTakenException(String message) {
        super(message);
    }
}
