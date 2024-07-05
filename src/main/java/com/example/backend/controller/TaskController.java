package com.example.backend.controller;


import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;
import com.example.backend.model.response.TaskResponse;
import com.example.backend.service.implement.TaskImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/taskstify/user")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {
    private final TaskImplement taskImplement;

    public TaskController(TaskImplement taskImplement) {
        this.taskImplement = taskImplement;
    }

    @GetMapping("/tasks/all")
    public ResponseEntity<TaskResponse<List<Task>>> getAllTasksForCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
        TaskResponse<List<Task>> response = TaskResponse.<List<Task>>builder()
                .payload(taskImplement.getAllTasksForCurrentUser(userId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse<Task>> getTaskByIDForCurrentUser(@PathVariable("id") Integer taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
            TaskResponse<Task> response = TaskResponse.<Task>builder()
                    .payload(taskImplement.getTaskByIDForCurrentUser(taskId, userId))
                    .date(new Timestamp(System.currentTimeMillis()))
                    .success("true")
                    .build();
            return ResponseEntity.ok(response);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse<Task>> insertTaskForCurrentUser(@RequestBody TaskRequest taskRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
        TaskResponse<Task> response = TaskResponse.<Task>builder()
                .payload(taskImplement.insertTaskForCurrentUser(taskRequest, userId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse<Task>> updateTaskForCurrentUser(@RequestBody TaskRequest taskRequest,@PathVariable("id") Integer taskId){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
            TaskResponse<Task> response = TaskResponse.<Task>builder()
                    .payload(taskImplement.updateTaskForCurrentUser(taskRequest, currentDate, taskId, userId))
                    .date(new Timestamp(System.currentTimeMillis()))
                    .success("true")
                    .build();
            return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse<Task>> deleteTaskForCurrentUser(@PathVariable("id") Integer taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
            TaskResponse<Task> response = TaskResponse.<Task>builder()
                    .payload(taskImplement.deleteTaskForCurrentUser(taskId, userId))
                    .date(new Timestamp(System.currentTimeMillis()))
                    .success("true")
                    .build();
            return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks/category/{categoryId}")
    public ResponseEntity<TaskResponse<List<Task>>> getTasksByCategoryForCurrentUser(@PathVariable("categoryId") Integer categoryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
        TaskResponse<List<Task>> response = TaskResponse.<List<Task>>builder()
                .payload(taskImplement.getTasksByCategory(categoryId, userId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

}
