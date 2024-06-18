package com.example.backend.repository;

import com.example.backend.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminRepository {
    @Select("SELECT * FROM users")
    List<User> getAllUsers();
    @Select("DELETE FROM users WHERE id = #{userId} AND role = 'USER' RETURNING id, email, role")
    User deleteUserByID(Integer userId);
}
