package com.example.backend.repository;

import com.example.backend.model.entity.UserInfo;

import com.example.backend.model.request.RegisterRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Mapper
public interface AuthRepository {

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results(id = "UserMapper", value = {
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role",column = "role")
    })
    Optional<UserInfo> findByEmail(@Param("email") String email);

    @Select("INSERT INTO users(name, email, password, role) " +
            "VALUES (#{request.name}, #{request.email}, #{request.password}, 'USER') RETURNING id")
    @RequestMapping("UserMapper")
    Integer saveUser(@Param("request") RegisterRequest registerRequest);


}
