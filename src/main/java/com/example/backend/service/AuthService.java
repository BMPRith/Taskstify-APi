package com.example.backend.service;

import com.example.backend.model.entity.UserInfo;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.model.response.AuthResponse;
import com.example.backend.repository.UserRepository;
import com.example.backend.securityconfig.JwtAuthenticationFilter;
import com.example.backend.securityconfig.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final JwtUtil jwtUtil;
        private final UserRepository userRepository;
        private final AuthenticationManager authenticationManager;
        public AuthResponse authenticate(AuthRequest request){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail());
            var jwtToken = jwtUtil.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken).build();
        }
    }

