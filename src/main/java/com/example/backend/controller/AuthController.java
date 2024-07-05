package com.example.backend.controller;


import com.example.backend.exception.EmailNotFound;
import com.example.backend.exception.EmailTaken;
import com.example.backend.exception.InvalidCredential;
import com.example.backend.model.entity.UserDTO;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.model.request.RegisterRequest;
import com.example.backend.model.response.AuthResponse;
import com.example.backend.model.response.UserResponse;
import com.example.backend.securityconfig.PasswordConfig;
import com.example.backend.service.AuthService;
import com.example.backend.service.EmailService;
import com.example.backend.service.implement.AuthImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RestController
@RequestMapping("/api/taskstify/home")
@SecurityRequirement(name= "bearerAuth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;
    private final AuthImplement authImplement;
    private final ModelMapper modelMapper;
    private final PasswordConfig passwordConfig;

    public AuthController(AuthService authService, AuthImplement authImplement, ModelMapper modelMapper, PasswordConfig passwordConfig, EmailService emailService) {
        this.authService = authService;
        this.authImplement = authImplement;
        this.modelMapper = modelMapper;
        this.passwordConfig = passwordConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthRequest authenticationRequest
    ) {
        try {
            AuthResponse userDetail = authService.authenticate(authenticationRequest);
            UserResponse<?> response = UserResponse.<AuthResponse>builder()
                    .payload(userDetail)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .success(true)
                    .build();
            return ResponseEntity.ok(response);
        } catch (InvalidCredential e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (EmailNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            registerRequest.setPassword(passwordConfig.passwordEncoder().encode(registerRequest.getPassword()));
            Integer storeUserId = authImplement.addNewUser(registerRequest);
            RegisterRequest user = new RegisterRequest(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword());

            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setId(storeUserId);

            UserResponse<?> response = UserResponse.<UserDTO>builder()
                    .payload(userDTO)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .success(true)
                    .build();

//            authService.sendVerificationEmail(registerRequest.getEmail());

            return ResponseEntity.ok(response);
        } catch (EmailTaken e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

//    @PostMapping("/send-reset-password")
//    public ResponseEntity<?> sendPasswordReset(@RequestParam String email) {
//        try {
//            authService.sendPasswordResetEmail(email);
//            return ResponseEntity.ok("Password reset email sent.");
//        } catch (EmailNotFound e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

}
