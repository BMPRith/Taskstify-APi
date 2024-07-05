package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CategoryNotFound.class)
    ProblemDetail HandleCategoryNotFound(CategoryNotFound e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Category Cannot Be Found");
        problemDetail.setDetail("Not Found");
        problemDetail.setType(URI.create("http://localhost:8080/api/taskstify/categories/error/not-found"));
        return problemDetail;
    }
    @ExceptionHandler(TaskNotFound.class)
    ProblemDetail HandleTaskNotFound(TaskNotFound e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Task Cannot Be Found");
        problemDetail.setDetail("Not Found");
        problemDetail.setType(URI.create("http://localhost:8080/api/taskstify/tasks/error/not-found"));
        return problemDetail;
    }
    @ExceptionHandler(UserNotFound.class)
    ProblemDetail HandleUserNotFound(UserNotFound e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("User Cannot Be Found");
        problemDetail.setDetail("Not Found");
        problemDetail.setType(URI.create("http://localhost:8080/api/taskstify/users/error/not-found"));
        return problemDetail;
    }
    @ExceptionHandler(StatusHandler.class)
    ProblemDetail HandleStatus(StatusHandler statusHandler){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, statusHandler.getMessage());
        problemDetail.setType(URI.create("localhost:8080/api/taskstify/error/bad-request"));
        problemDetail.setTitle("Wrong Status Error");
        problemDetail.setProperty("time", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(BlankFieldHandler.class)
    ProblemDetail HandleBlankField(BlankFieldHandler blankFieldHandler){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, blankFieldHandler.getMessage());
        problemDetail.setType(URI.create("localhost:8080/api/taskstify/error/bad-request"));
        problemDetail.setTitle("Blank Field Error");
        problemDetail.setProperty("time", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(CategoryNotExisted.class)
    ProblemDetail HandleCategoryNotExisted(CategoryNotExisted categoryNotExisted){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, categoryNotExisted.getMessage());
        problemDetail.setType(URI.create("localhost:8080/api/taskstify/error/not-found"));
        problemDetail.setTitle("Category Doesn't Existed");
        problemDetail.setProperty("time", LocalDateTime.now());
        return problemDetail;
    }
    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredential e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(EmailNotFound.class)
    public ResponseEntity<String> handleEmailNotFoundException(EmailNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(EmailTaken.class)
    public ResponseEntity<String> handleEmailTakenException(EmailTaken e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

}
