package com.example.backend.service;

import com.example.backend.model.entity.User;

import java.util.List;


public interface AdminService {
    List<User> getAllUsers();
    User deleteUserByID(Integer userId);
}
