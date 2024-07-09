package com.example.backend.repository;

import com.example.backend.model.entity.UserInfo;
import com.example.backend.model.request.RegisterRequest;
import org.apache.ibatis.annotations.*;
import java.util.Optional;

@Mapper
public interface AuthRepository {

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results(id = "UserMapper", value = {
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role")
    })
    Optional<UserInfo> findByEmail(@Param("email") String email);

    @Insert("INSERT INTO users(name, email, password, role) " +
            "VALUES (#{request.name}, #{request.email}, #{request.password}, 'USER') RETURNING id")
    @ResultMap("UserMapper")
    Integer saveUser(@Param("request") RegisterRequest registerRequest);

    @Update("UPDATE users SET password = #{password} WHERE email = #{email}")
    void updatePassword(@Param("email") String email, @Param("password") String password);
}
