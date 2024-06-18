package com.example.backend.exception;

public class CategoryNotFound extends RuntimeException{
    public CategoryNotFound(Integer categoryId){
        super("Category With ID " + categoryId + " Cannot Be Found");
    }
}
