package com.example.backend.controller;


import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;
import com.example.backend.model.response.TaskResponse;
import com.example.backend.service.implement.TaskImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {
    private final TaskImplement taskImplement;

    public TaskController(TaskImplement taskImplement) {
        this.taskImplement = taskImplement;
    }
    @GetMapping("/all")
    public ResponseEntity<TaskResponse<List<Task>>> getAllTasks(@RequestParam Integer page, Integer size){
        TaskResponse<List<Task>> response = TaskResponse.<List<Task>>builder()
                .payload(taskImplement.getAllTasks(page,size))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse<Task>> getTaskByID(@PathVariable("id") Integer taskId){
        TaskResponse<Task> response = TaskResponse.<Task>builder()
                .payload(taskImplement.getTaskByID(taskId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/users")
    public ResponseEntity<TaskResponse<List<Task>>> getAllTasksUser(@RequestParam Integer page, Integer size){
        TaskResponse<List<Task>> response = TaskResponse.<List<Task>>builder()
                .payload(taskImplement.getAllTasksUser(page,size))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<TaskResponse<Task>> getTaskByIDUser(@PathVariable("id") Integer taskId){
        TaskResponse<Task> response = TaskResponse.<Task>builder()
                .payload(taskImplement.getTaskByIDUser(taskId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users")
    public ResponseEntity<TaskResponse<Task>> insertTask(@RequestBody TaskRequest taskRequest){
        TaskResponse<Task> response = TaskResponse.<Task>builder()
                .payload(taskImplement.insertTask(taskRequest))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/users")
    public ResponseEntity<TaskResponse<Task>> updateTask(@RequestBody TaskRequest taskRequest,@PathVariable("id") Integer taskId){
        TaskResponse<Task> response = TaskResponse.<Task>builder()
                .payload(taskImplement.updateTask(taskRequest, taskId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/users")
    public ResponseEntity<TaskResponse<Task>> deleteTask(@PathVariable("id") Integer taskId){
        TaskResponse<Task> response = TaskResponse.<Task>builder()
                .payload(taskImplement.deleteTask(taskId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }
}
