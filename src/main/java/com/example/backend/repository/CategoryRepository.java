package com.example.backend.repository;

import com.example.backend.model.entity.Category;
import com.example.backend.model.request.CategoryRequest;
import org.apache.ibatis.annotations.*;


import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface CategoryRepository {
    @Results(id = "Mapper", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "date", column = "date"),
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT * FROM categories")
    List<Category> getAllCategories();
    @Select("SELECT * FROM categories WHERE id = #{categoryId}")
    @ResultMap("Mapper")
    Category getCategoryByID(Integer categoryId);
    @Select("INSERT INTO categories (name, date, user_id) VALUES (#{request.name}, #{date}, #{userId}) RETURNING id, name, date, user_id")
    @ResultMap("Mapper")
    Category insertCategoryForCurrentUser(@Param("request") CategoryRequest name, @Param("date") Timestamp date, @Param("userId") Integer userId);
    @Select("UPDATE categories SET name = #{request.name} WHERE id = #{categoryId} AND user_id = #{userId} RETURNING id, name, date, user_id")
    @ResultMap("Mapper")
    Category updateCategoryForCurrentUser(@Param("request")CategoryRequest categoryRequest, Integer categoryId, Integer userId);
    @Select("DELETE FROM categories WHERE id = #{categoryId} AND user_id = #{userId} RETURNING id, name, date, user_id")
    @ResultMap("Mapper")
    Category deleteCategoryForCurrentUser(Integer categoryId, Integer userId);
    @Select("SELECT * FROM categories WHERE user_id = #{userId}")
    @ResultMap("Mapper")
    List<Category> getAllCategoriesForCurrentUser(@Param("userId") Integer userId);
    @Select("SELECT * FROM categories WHERE id = #{categoryId} AND user_id = #{userId}")
    @ResultMap("Mapper")
    Category getCategoryByIDForCurrentUser(Integer categoryId, @Param("userId") Integer userId);
}
