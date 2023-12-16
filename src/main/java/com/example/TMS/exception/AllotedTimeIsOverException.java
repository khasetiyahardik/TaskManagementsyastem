package com.example.TMS.exception;

public class AllotedTimeIsOverException extends RuntimeException{
    public AllotedTimeIsOverException(String message) {
        super(message);
    }
}
