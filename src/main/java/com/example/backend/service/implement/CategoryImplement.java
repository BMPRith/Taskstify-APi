package com.example.backend.service.implement;

import com.example.backend.exception.BlankFieldHandler;
import com.example.backend.exception.CategoryNotFound;
import com.example.backend.model.entity.Category;
import com.example.backend.model.request.CategoryRequest;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.service.AuthService;
import com.example.backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
            return search;
        } else throw new CategoryNotFound(categoryId);
    }

    @Override
    public Category insertCategoryForCurrentUser(CategoryRequest categoryRequest, Integer userId, Timestamp date) {
        if(categoryRequest.getName().isBlank()){
            throw  new BlankFieldHandler("Field Cannot Be Blank");
        } else {
            return categoryRepository.insertCategoryForCurrentUser(categoryRequest,date, userId);
        }
    }

    @Override
    public Category deleteCategoryForCurrentUser(Integer categoryId, Integer userId) {
        Category delete =  categoryRepository.deleteCategoryForCurrentUser(categoryId,userId);
        if (delete!=null) {
            return delete;
        } else throw new CategoryNotFound(categoryId);
    }

    @Override
    public Category updateCategoryForCurrentUser(CategoryRequest categoryRequest, Integer categoryId, Integer userId) {
        Category update =  categoryRepository.updateCategoryForCurrentUser(categoryRequest,categoryId,userId);
        if (update==null) {
            throw new CategoryNotFound(categoryId);
        } else if(categoryRequest.getName().isBlank()){
            throw  new BlankFieldHandler("Field Cannot Be Blank");
        } else {
            return update;
        }
    }

    @Override
    public List<Category> getAllCategoriesForCurrentUser(Integer userId, Integer page, Integer size) {
        return categoryRepository.getAllCategoriesForCurrentUser(userId, page, size);
    }

    @Override
    public Category getCategoryByIDForCurrentUser(Integer categoryId, Integer userId) {
        Category search = categoryRepository.getCategoryByIDForCurrentUser(categoryId, userId);
        if (search!=null) {
            return search;
        } else throw new CategoryNotFound(categoryId);
    }
}
