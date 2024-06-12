package com.example.backend.controller;

import com.example.backend.model.entity.Category;
import com.example.backend.model.request.CategoryRequest;
import com.example.backend.model.response.CategoryResponse;
import com.example.backend.service.AuthService;
import com.example.backend.service.implement.CategoryImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryImplement categoryImplement;

    public CategoryController(CategoryImplement categoryImplement) {
        this.categoryImplement = categoryImplement;
    }

    @GetMapping("/all")
    public ResponseEntity<CategoryResponse<List<Category>>> getAllCategories(@RequestParam Integer page, Integer size){
       CategoryResponse<List<Category>> response = CategoryResponse.<List<Category>>builder()
               .payload(categoryImplement.getAllCategories(page,size))
               .date(new Timestamp(System.currentTimeMillis()))
               .success("true")
               .build();
       return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse<Category>> getCategoryByID(@PathVariable("id") Integer categoryId){
        CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                .payload(categoryImplement.getCategoryByID(categoryId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/users")
    public ResponseEntity<CategoryResponse<List<Category>>> getAllCategoriesUser(@RequestParam Integer page, Integer size){
        CategoryResponse<List<Category>> response = CategoryResponse.<List<Category>>builder()
                .payload(categoryImplement.getAllCategoriesUser(page,size))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<CategoryResponse<Category>> getCategoryByIDUser(@PathVariable("id") Integer categoryId){
        CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                .payload(categoryImplement.getCategoryByIDUser(categoryId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users")
    public ResponseEntity<CategoryResponse<Category>> insertCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                .payload(categoryImplement.insertCategory(categoryRequest))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/users")
    public ResponseEntity<CategoryResponse<Category>> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable("id") Integer categoryId){
        CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                .payload(categoryImplement.updateCategory(categoryRequest, categoryId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/users")
    public ResponseEntity<CategoryResponse<Category>> deleteCategory(@PathVariable("id") Integer categoryId){
        CategoryResponse<Category> response = CategoryResponse.<Category>builder()
                .payload(categoryImplement.deleteCategory(categoryId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }
}
