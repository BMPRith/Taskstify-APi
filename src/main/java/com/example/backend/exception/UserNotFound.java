package com.example.backend.exception;

public class UserNotFound extends RuntimeException{
    public UserNotFound(Integer userId){
        super("Task with ID " + userId + " Cannot Be Found");
    }
}
