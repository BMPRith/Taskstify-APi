package com.example.backend.exception;

public class TaskNotFound extends RuntimeException{
    public TaskNotFound(Integer taskId){
        super("Task with ID " + taskId + " Cannot Be Found");
    }
}

