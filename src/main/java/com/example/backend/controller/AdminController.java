package com.example.backend.controller;

import com.example.backend.model.entity.Category;
import com.example.backend.model.entity.Task;
import com.example.backend.model.entity.User;
import com.example.backend.model.response.CategoryResponse;
import com.example.backend.model.response.TaskResponse;
import com.example.backend.model.response.UserResponse;
import com.example.backend.service.implement.AdminImplement;
import com.example.backend.service.implement.CategoryImplement;
import com.example.backend.service.implement.TaskImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/taskstify/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    private final CategoryImplement categoryImplement;
    private final AdminImplement adminImplement;
    private final TaskImplement taskImplement;

    public AdminController(CategoryImplement categoryImplement, AdminImplement adminImplement, TaskImplement taskImplement) {
        this.categoryImplement = categoryImplement;
        this.adminImplement = adminImplement;
        this.taskImplement = taskImplement;
    }

    @GetMapping("/categories/all")
    public ResponseEntity<CategoryResponse<List<Category>>> getAllCategories(@RequestParam Integer page, Integer size){
        CategoryResponse<List<Category>> response = CategoryResponse.<List<Category>>builder()
                .payload(categoryImplement.getAllCategories(page,size))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse<Category>> getCategoryByID(@PathVariable("id") Integer categoryId){
        CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                .payload(categoryImplement.getCategoryByID(categoryId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks/all")
    public ResponseEntity<TaskResponse<List<Task>>> getAllTasks(@RequestParam Integer page, Integer size){
        TaskResponse<List<Task>> response = TaskResponse.<List<Task>>builder()
                .payload(taskImplement.getAllTasks(page,size))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse<Task>> getTaskByID(@PathVariable("id") Integer taskId){
        TaskResponse<Task> response = TaskResponse.<Task>builder()
                .payload(taskImplement.getTaskByID(taskId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("users/all")
    public ResponseEntity<UserResponse<List<User>>> getAllUsers(){
        UserResponse<List<User>> response = UserResponse.<List<User>>builder()
                .payload(adminImplement.getAllUsers())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserResponse<User>> deleteUserByID(@PathVariable("id") Integer userId){
        UserResponse<User> response = UserResponse.<User>builder()
                .payload(adminImplement.deleteUserByID(userId))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
