package com.booot.pos_system_project.service;

import com.booot.pos_system_project.dto.request.CategoryRequest;
import com.booot.pos_system_project.dto.response.CategoryResponse;
import com.booot.pos_system_project.dto.response.CategoryTreeResponse;
import com.booot.pos_system_project.enums.common.ActiveStatus;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(Long id, CategoryRequest request);

    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    List<CategoryTreeResponse> getAllCategoriesTree();

    CategoryResponse changeStatus(Long id, ActiveStatus status);
    void deleteCategory(Long id);
}
