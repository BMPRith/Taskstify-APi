package com.example.backend.service;

import com.example.backend.exception.EmailNotFound;
import com.example.backend.exception.InvalidCredential;
import com.example.backend.model.entity.UserInfo;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.model.response.AuthResponse;
import com.example.backend.repository.AuthRepository;
import com.example.backend.securityconfig.JwtUtil;
import com.example.backend.service.implement.AuthImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final AuthImplement authImplement;

    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredential("Invalid email or password");
        }

        var user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFound("Email not found"));
        var jwtToken = jwtUtil.generateToken(user, request.isRememberMe());
        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

//    public void sendVerificationEmail(String email) {
//        String code = generateVerificationCode();
//        authImplement.saveVerificationCode(email, code);
//        emailService.sendVerificationEmail(email, code);
//    }

//    public void sendPasswordResetEmail(String email) {
//        String token = generatePasswordResetToken();
//        authImplement.savePasswordResetToken(email, token);
//        String resetLink = "http://localhost:3000/reset-password?token=" + token;
//        emailService.sendPasswordResetEmail(email, resetLink);
//    }
//
//    private String generateVerificationCode() {
//        return String.valueOf((int)((Math.random() * 9000) + 1000));
//    }
//
//    private String generatePasswordResetToken() {
//        return UUID.randomUUID().toString();
//    }
}
