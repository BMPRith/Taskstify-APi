package com.example.backend.controller;


import com.example.backend.model.entity.UserDTO;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.model.response.AuthResponse;
import com.example.backend.model.response.UserResponse;
import com.example.backend.securityconfig.PasswordConfig;
import com.example.backend.service.AuthService;
import com.example.backend.service.implement.AuthImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;


@RestController
@RequestMapping("/api/taskstify/home")
@SecurityRequirement(name= "bearerAuth")
public class AuthController {
    private final AuthService authService;
    private final AuthImplement authImplement;
    private final ModelMapper modelMapper;
    private final PasswordConfig passwordConfig;

    public AuthController(AuthService authService, AuthImplement authImplement, ModelMapper modelMapper, PasswordConfig passwordConfig) {
        this.authService = authService;
        this.authImplement = authImplement;
        this.modelMapper = modelMapper;
        this.passwordConfig = passwordConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthRequest authenticationRequest
    ){
        AuthResponse userDetail = authService.authenticate(authenticationRequest);
        UserResponse<?> response = UserResponse.<AuthResponse>builder()
                .payload(userDetail)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        authRequest.setPassword(passwordConfig.passwordEncoder().encode(authRequest.getPassword()));
        Integer storeUserId = authImplement.addNewUser(authRequest);
        AuthRequest user = new AuthRequest(authRequest.getEmail(), authRequest.getPassword());

        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        userDTO.setId(storeUserId);

        UserResponse<?> response = UserResponse.<UserDTO>builder()
                .payload(userDTO)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

}
