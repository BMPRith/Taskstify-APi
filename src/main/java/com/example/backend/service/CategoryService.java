package com.example.backend.service;

import com.example.backend.model.entity.Category;
import com.example.backend.model.request.CategoryRequest;

import java.sql.Timestamp;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories(Integer page, Integer size);
    Category getCategoryByID(Integer categoryId);
    Category insertCategoryForCurrentUser(CategoryRequest categoryRequest, Integer userId, Timestamp date);
    Category deleteCategoryForCurrentUser(Integer categoryId, Integer userId);
    Category updateCategoryForCurrentUser(CategoryRequest categoryRequest, Integer categoryId, Integer userId);
    List<Category> getAllCategoriesForCurrentUser(Integer userId, Integer page, Integer size);
    Category getCategoryByIDForCurrentUser(Integer categoryId, Integer userId);
}
