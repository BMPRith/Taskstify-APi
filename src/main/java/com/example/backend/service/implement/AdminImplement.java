package com.example.backend.service.implement;

import com.example.backend.exception.UserNotFound;
import com.example.backend.model.entity.User;

import com.example.backend.repository.AdminRepository;
import com.example.backend.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminImplement implements AdminService {
    private final AdminRepository adminRepository;

    public AdminImplement(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return adminRepository.getAllUsers();
    }

    @Override
    public User deleteUserByID(Integer userId) {
        User delete = adminRepository.deleteUserByID(userId);
        if(delete != null){
            return delete;
        } else throw new UserNotFound(userId);
    }
}
