package com.example.backend.service.implement;

import com.example.backend.exception.CategoryNotFound;
import com.example.backend.model.entity.Category;
import com.example.backend.model.request.CategoryRequest;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.service.AuthService;
import com.example.backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryImplement implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryImplement(CategoryRepository categoryRepository, AuthService authService) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories(Integer page, Integer size) {
        return categoryRepository.getAllCategories(page, size);
    }

    @Override
    public Category getCategoryByID(Integer categoryId) {
        Category search = categoryRepository.getCategoryByID(categoryId);
        if (search!=null) {
            return categoryRepository.getCategoryByID(categoryId);
        } else throw new CategoryNotFound(categoryId);
    }

    @Override
    public Category insertCategory(CategoryRequest categoryRequest) {
        return categoryRepository.insertCategory(categoryRequest);
    }

    @Override
    public Category deleteCategory(Integer categoryId) {
        Category delete = categoryRepository.deleteCategory(categoryId);
        if (delete!=null) {
            return categoryRepository.deleteCategory(categoryId);
        } else throw new CategoryNotFound(categoryId);
    }

    @Override
    public Category updateCategory(CategoryRequest categoryRequest, Integer categoryId) {
        return categoryRepository.updateCategory(categoryRequest, categoryId);
    }

    @Override
    public List<Category> getAllCategoriesUser(Integer page, Integer size) {
        return categoryRepository.getAllCategoriesUser(page, size);
    }

    @Override
    public Category getCategoryByIDUser(Integer categoryId) {
        Category search = categoryRepository.getCategoryByIDUser(categoryId);
        if (search!=null) {
            return categoryRepository.getCategoryByIDUser(categoryId);
        } else throw new CategoryNotFound(categoryId);
    }
}
