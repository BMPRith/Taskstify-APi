package com.example.backend.exception;

public class CategoryNotExisted extends RuntimeException{
    public CategoryNotExisted(Integer categoryId){
        super("Category With ID " + categoryId + " Doesn't Exist In Your Account");
    }
}

