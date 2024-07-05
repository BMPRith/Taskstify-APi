package com.example.backend.model.entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
public class UserDTO {
    private String name;
    private Integer id;
    private String email;

}
