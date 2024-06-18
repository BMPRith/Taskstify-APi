package com.example.backend.exception;


public class BlankFieldHandler extends RuntimeException{
    public BlankFieldHandler(String message) {
        super(message);
    }
}
