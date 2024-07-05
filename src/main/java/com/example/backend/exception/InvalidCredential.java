package com.example.backend.exception;

public class InvalidCredential extends RuntimeException {
    public InvalidCredential(String message) {
        super(message);
    }
}