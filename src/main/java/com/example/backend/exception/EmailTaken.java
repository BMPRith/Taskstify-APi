package com.example.backend.exception;

public class EmailTaken extends RuntimeException{
    public EmailTaken(String message) {
        super(message);
    }
}
