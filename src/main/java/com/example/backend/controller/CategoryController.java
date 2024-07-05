package com.example.backend.controller;

import com.example.backend.model.entity.Category;
import com.example.backend.model.request.CategoryRequest;
import com.example.backend.model.response.CategoryResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.backend.service.implement.CategoryImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/taskstify/user")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {
    private final CategoryImplement categoryImplement;

    public CategoryController(CategoryImplement categoryImplement) {
        this.categoryImplement = categoryImplement;
    }

    @GetMapping("/categories/all")
    public ResponseEntity<CategoryResponse<List<Category>>> getAllCategoriesForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
        CategoryResponse<List<Category>> response = CategoryResponse.<List<Category>>builder()
                .payload(categoryImplement.getAllCategoriesForCurrentUser(userId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse<Category>> getCategoryByIDForCurrentUser(@PathVariable("id") Integer categoryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
            CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                    .payload(categoryImplement.getCategoryByIDForCurrentUser(categoryId,userId))
                    .date(new Timestamp(System.currentTimeMillis()))
                    .success("true")
                    .build();
            return ResponseEntity.ok(response);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse<Category>> insertCategoryForCurrentUser(@RequestBody CategoryRequest categoryRequest){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
        CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                .payload(categoryImplement.insertCategoryForCurrentUser(categoryRequest,userId,currentDate))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse<Category>> updateCategoryForCurrentUser(@RequestBody CategoryRequest categoryRequest, @PathVariable("id") Integer categoryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
                CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                        .payload(categoryImplement.updateCategoryForCurrentUser(categoryRequest,categoryId,userId))
                        .date(new Timestamp(System.currentTimeMillis()))
                        .success("true")
                        .build();
                return ResponseEntity.ok(response);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse<Category>> deleteCategoryForCurrentUser(@PathVariable("id") Integer categoryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
            CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                    .payload(categoryImplement.deleteCategoryForCurrentUser(categoryId,userId))
                    .date(new Timestamp(System.currentTimeMillis()))
                    .success("true")
                    .build();
            return ResponseEntity.ok(response);
    }
}
