package com.example.backend.service;

import com.example.backend.model.entity.Category;
import com.example.backend.model.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories(Integer page, Integer size);
    Category getCategoryByID(Integer categoryId);
    Category insertCategory(CategoryRequest categoryRequest);
    Category deleteCategory(Integer categoryId);
    Category updateCategory(CategoryRequest categoryRequest, Integer categoryId);
    List<Category> getAllCategoriesUser(Integer page, Integer size);
    Category getCategoryByIDUser(Integer categoryId);
}
