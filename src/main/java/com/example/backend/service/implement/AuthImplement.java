package com.example.backend.service.implement;

import com.example.backend.model.entity.UserInfo;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthImplement implements UserDetailsService {
    private final AuthRepository authRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = authRepository.findByEmail(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return userInfo;
    }

    public Integer addNewUser(AuthRequest authRequest) {
        return authRepository.saveUser(authRequest);
    }
}
