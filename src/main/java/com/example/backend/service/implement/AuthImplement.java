package com.example.backend.service.implement;

import com.example.backend.exception.EmailNotFound;
import com.example.backend.exception.EmailTaken;
import com.example.backend.model.entity.UserInfo;
import com.example.backend.model.request.RegisterRequest;
import com.example.backend.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
@Service
@AllArgsConstructor
public class AuthImplement implements UserDetailsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> verificationCodeExpirations = new ConcurrentHashMap<>();
    private final Map<String, String> passwordResetTokens = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> passwordResetTokenExpirations = new ConcurrentHashMap<>();

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFound {
        return authRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFound("Email Doesn't Exist"));
    }

    public Integer addNewUser(RegisterRequest registerRequest) {
        Optional<UserInfo> existingEmail = authRepository.findByEmail(registerRequest.getEmail());
        if (existingEmail.isEmpty()) {
            return authRepository.saveUser(registerRequest);
        } else {
            throw new EmailTaken("Email Already Taken");
        }
    }

    public void saveVerificationCode(String email, String code) {
        verificationCodes.remove(email, code);
        verificationCodeExpirations.remove(email, code);

        verificationCodes.put(email, code);
        verificationCodeExpirations.put(email, LocalDateTime.now().plusMinutes(10));
    }

    public String getVerificationCode(String email) {
        return verificationCodes.get(email);
    }

    public LocalDateTime getVerificationCodeExpiration(String email) {
        return verificationCodeExpirations.get(email);
    }

    public boolean isVerificationCodeExpired(String email) {
        LocalDateTime expirationTime = getVerificationCodeExpiration(email);
        return expirationTime != null && LocalDateTime.now().isAfter(expirationTime);
    }

    public void savePasswordResetToken(String email, String token) {
        passwordResetTokens.put(email, token);
        passwordResetTokenExpirations.put(email, LocalDateTime.now().plusMinutes(30));
    }

    public String getPasswordResetToken(String email) {
        return passwordResetTokens.get(email);
    }

    public LocalDateTime getPasswordResetTokenExpiration(String email) {
        return passwordResetTokenExpirations.get(email);
    }

    public String getEmailByToken(String token) {
        return passwordResetTokens.entrySet().stream()
                .filter(entry -> entry.getValue().equals(token))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public void updatePassword(String email, String newPassword) {
        Optional<UserInfo> optionalUser = authRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserInfo user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            authRepository.updatePassword(email, user.getPassword());
        } else {
            throw new EmailNotFound("Email not found");
        }
    }

    public void removePasswordResetToken(String email) {
        passwordResetTokens.remove(email);
        passwordResetTokenExpirations.remove(email);
    }
}
