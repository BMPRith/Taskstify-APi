package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CategoryNotFound.class)
    ProblemDetail HandleCategoryNotFound(CategoryNotFound e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Category Cannot Be Found");
        problemDetail.setDetail("Not Found");
        problemDetail.setType(URI.create("http://localhost:8080/api/v1/categories/error/not-found"));
        return problemDetail;
    }

}
