package com.example.backend.controller;


import com.example.backend.model.entity.UserDTO;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.model.response.AuthResponse;
import com.example.backend.model.response.UserResponse;
import com.example.backend.securityconfig.PasswordConfig;
import com.example.backend.service.AuthService;
import com.example.backend.service.implement.UserServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SecurityRequirement(name= "bearerAuth")
@RestController
@RequestMapping("/api/v1/home")
public class AuthController {
    private final AuthService authService;
    private final UserServiceImp userServiceImp;
    List<AuthRequest> userArray = new ArrayList<>();
    private final ModelMapper modelMapper;
    private final PasswordConfig passwordConfig;

    public AuthController(AuthService authService, UserServiceImp userServiceImp, ModelMapper modelMapper, PasswordConfig passwordConfig) {
        this.authService = authService;
        this.userServiceImp = userServiceImp;
        this.modelMapper = modelMapper;
        this.passwordConfig = passwordConfig;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthRequest authenticationRequest
    ){
        var userDetail = authService.authenticate(authenticationRequest);
        UserResponse<?> response = UserResponse.<AuthResponse>builder()
                .payload(userDetail)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .success(true)
                .build();
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        authRequest.setPassword(passwordConfig.passwordEncoder().encode(authRequest.getPassword()));
        Integer storeUserId = userServiceImp.addNewUser(authRequest);
        AuthRequest user = new AuthRequest(authRequest.getEmail(), authRequest.getPassword());
//        userArray.add(user);

        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        userDTO.setId(storeUserId);
//        Auth auth = new Auth();
//        auth.setId(storeUserId);

        UserResponse<?> response = UserResponse.<UserDTO>builder()
                .payload(userDTO)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

}
