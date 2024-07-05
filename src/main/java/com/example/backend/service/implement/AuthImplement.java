package com.example.backend.service.implement;

import com.example.backend.exception.EmailNotFound;
import com.example.backend.exception.EmailTaken;
import com.example.backend.model.entity.UserInfo;
import com.example.backend.model.request.RegisterRequest;
import com.example.backend.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthImplement implements UserDetailsService {
    private final AuthRepository authRepository;
    private Map<String, String> verificationCodes = new HashMap<>();
    private Map<String, String> passwordResetTokens = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFound {
        return authRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFound("Email Doesn't Exists"));
    }

    public Integer addNewUser(RegisterRequest registerRequest) {
        Optional<UserInfo> existingEmail = authRepository.findByEmail(registerRequest.getEmail());
        if (existingEmail.isEmpty()){
            return authRepository.saveUser(registerRequest);
        } else {
            throw new EmailTaken("Email Already Taken");
        }
    }

    public void saveVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
    }

    public String getVerificationCode(String email) {
        return verificationCodes.get(email);
    }

    public void savePasswordResetToken(String email, String token) {
        passwordResetTokens.put(email, token);
    }

    public String getPasswordResetToken(String email) {
        return passwordResetTokens.get(email);
    }
}

