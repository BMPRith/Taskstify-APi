package com.example.backend.service;

import com.example.backend.exception.EmailNotFound;
import com.example.backend.exception.InvalidCredential;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.model.response.AuthResponse;
import com.example.backend.repository.AuthRepository;
import com.example.backend.securityconfig.JwtUtil;
import com.example.backend.service.implement.AuthImplement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final AuthImplement authImplement;
    private final ModelMapper modelMapper;

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

    public String sendVerificationEmail(String email) {
        String code = generateVerificationCode();
        authImplement.saveVerificationCode(email, code);
        emailService.sendVerificationEmail(email, code);
        return code;
    }

    public void sendPasswordResetEmail(String email) {
        String token = generatePasswordResetToken();
        authImplement.savePasswordResetToken(email, token);
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(email, resetLink);
    }

    public boolean verifyCode(String email, String code) {
        String savedCode = authImplement.getVerificationCode(email);
        LocalDateTime expirationTime = authImplement.getVerificationCodeExpiration(email);
        if (LocalDateTime.now().isAfter(expirationTime)) {
            throw new RuntimeException("Verification code expired");
        }
        return savedCode.equals(code);
    }


    public boolean verifyResetToken(String token) {
        String email = authImplement.getEmailByToken(token);
        LocalDateTime expirationTime = authImplement.getPasswordResetTokenExpiration(email);

        if (email == null || expirationTime == null) {
            throw new RuntimeException("Invalid password reset token");
        }

        if (LocalDateTime.now().isAfter(expirationTime)) {
            throw new RuntimeException("Password reset token expired");
        }
        return true;
    }

    private String generatePasswordResetToken() {
        return UUID.randomUUID().toString();
    }

    private String generateVerificationCode() {
        return String.valueOf((int)((Math.random() * 9000) + 1000));
    }
}
