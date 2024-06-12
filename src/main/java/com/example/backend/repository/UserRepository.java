package com.example.backend.repository;

import com.example.backend.model.entity.UserInfo;
import com.example.backend.model.request.AuthRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results(id = "UserMapper", value = {
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role",column = "role")
    })
    UserInfo findByEmail(@Param("email") String email);
    @Select("SELECT * FROM users WHERE id = #{userId}")
    @RequestMapping("UserMapper")
    UserInfo getByID(Integer userId);

    @Select("INSERT INTO users(email, password, role) " +
            "VALUES (#{request.email}, #{request.password}, 'USER') RETURNING id")
    @RequestMapping("UserMapper")
    Integer saveUser(@Param("request") AuthRequest authRequest);



}
