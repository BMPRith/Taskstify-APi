package com.example.backend.controller;


import com.example.backend.exception.EmailNotFound;
import com.example.backend.exception.EmailTaken;
import com.example.backend.exception.InvalidCredential;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.model.request.RegisterRequest;
import com.example.backend.model.response.AuthResponse;
import com.example.backend.model.response.UserResponse;
import com.example.backend.securityconfig.PasswordConfig;
import com.example.backend.service.AuthService;
import com.example.backend.service.implement.AuthImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/taskstify/home")
@SecurityRequirement(name= "bearerAuth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;
    private final AuthImplement authImplement;
    private final ModelMapper modelMapper;
    private final PasswordConfig passwordConfig;

    private Map<String, RegisterRequest> pendingRegistrations = new HashMap<>();

    public AuthController(AuthService authService, AuthImplement authImplement, ModelMapper modelMapper, PasswordConfig passwordConfig) {
        this.authService = authService;
        this.authImplement = authImplement;
        this.modelMapper = modelMapper;
        this.passwordConfig = passwordConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authenticationRequest) {
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
            authService.sendVerificationEmail(registerRequest.getEmail());
            pendingRegistrations.put(registerRequest.getEmail(), registerRequest);
            return ResponseEntity.ok("Verification code sent to your email. Please check and verify.");
        } catch (EmailTaken e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        try {
            if (authService.verifyCode(email, code)) {
                RegisterRequest registerRequest = pendingRegistrations.get(email);
                if (registerRequest != null) {
                    registerRequest.setPassword(passwordConfig.passwordEncoder().encode(registerRequest.getPassword()));
                    authImplement.addNewUser(registerRequest);
                    pendingRegistrations.remove(email);
                    return ResponseEntity.ok("Email verified successfully. Account created.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No registration found for this email.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.GONE).body("Verification code expired");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            authService.sendPasswordResetEmail(email);
            return ResponseEntity.ok("Password reset link sent.");
        } catch (EmailNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("password");

        String email = authImplement.getEmailByToken(token);
        if (email != null) {
            authImplement.updatePassword(email, newPassword);
            authImplement.removePasswordResetToken(email);
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired password reset token.");
        }
    }
}

