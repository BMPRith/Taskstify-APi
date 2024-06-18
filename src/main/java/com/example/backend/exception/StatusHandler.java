package com.example.backend.exception;

public class StatusHandler extends RuntimeException{
    public StatusHandler(String message){
        super(message);
    }
}
