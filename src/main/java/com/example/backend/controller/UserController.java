package com.example.backend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @GetMapping("/home")
    public String getHome(){
        return "Hello from home page";
    }
    @GetMapping("/admin")
    public String getAdmin(){
        return "Hello from admin page";
    }
    @GetMapping("/users")
    public String getUser(){
        return "Hello from User page";
    }
}
