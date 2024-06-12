package com.example.backend.repository;

import com.example.backend.model.entity.Category;
import com.example.backend.model.request.CategoryRequest;
import org.apache.ibatis.annotations.*;


import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CategoryRepository {
    @Select("SELECT * FROM categories LIMIT ${size} OFFSET #{size} * (#{page} -1)")
    List<Category> getAllCategories(Integer page, Integer size);
    @Select("SELECT * FROM categories WHERE id = #{categoryId}")
    Category getCategoryByID(Integer categoryId);
    @Insert("INSERT INTO categories (name, date, user_id) VALUES (#{request.name}, #{date}, #{userId}) RETURNING id")
    @Result(property = "userId", column = "user_id",
            one = @One(select = "com.example.backend.repository.UserRepository.getByID")
    )
    Category insertCategory(@Param("request") CategoryRequest categoryRequest);
    @Select("UPDATE categories SET name = #{request.name} WHERE id = {categoryId}")
    Category updateCategory(@Param("request")CategoryRequest categoryRequest, Integer categoryId);
    @Select("DELETE FROM categories WHERE id = #{categoryId}")
    Category deleteCategory(Integer categoryId);
    @Select("SELECT * FROM categories LIMIT ${size} OFFSET #{size} * (#{page} -1)")
    List<Category> getAllCategoriesUser(Integer page, Integer size);
    @Select("SELECT c.*, u.id AS userId FROM categories c INNER JOIN users u ON c.user_id = u.id WHERE c.id = #{categoryId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "date", column = "date"),
            @Result(property = "userId", column = "user_id")
    })
    Category getCategoryByIDUser(@Param("categoryId") Integer categoryId);
}
