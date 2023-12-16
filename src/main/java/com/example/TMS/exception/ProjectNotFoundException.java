package com.example.TMS.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
