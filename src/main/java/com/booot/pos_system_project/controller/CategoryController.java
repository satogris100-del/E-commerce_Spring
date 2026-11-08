package com.booot.pos_system_project.controller;

import com.booot.pos_system_project.dto.request.CategoryRequest;
import com.booot.pos_system_project.dto.request.CategoryStatusRequest;
import com.booot.pos_system_project.dto.response.ApiResponse;
import com.booot.pos_system_project.dto.response.CategoryResponse;
import com.booot.pos_system_project.dto.response.CategoryTreeResponse;
import com.booot.pos_system_project.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Category Management APIs")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ---------- Public ----------
    @GetMapping("/tree")
    @Operation(summary = "Get active categories as a tree (storefront menu)")
    public ResponseEntity<ApiResponse<List<CategoryTreeResponse>>> getTree() {
        return ResponseEntity.ok(
                ApiResponse.success("Get category tree successfully",
                        categoryService.getAllCategoriesTree()));
    }

    // ---------- STAFF + ADMIN ----------

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    @Operation(summary = "Get all categories (staff, admin)")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return ResponseEntity.ok(
                ApiResponse.success("Get all categories successfully",
                        categoryService.getAllCategories()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    @Operation(summary = "Get category by id (staff, admin)")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Get category successfully",
                        categoryService.getCategoryById(id)));
    }

    // ---------- ADMIN only ----------

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new category (admin)")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.createCategory(request);
        return new ResponseEntity<>(
                ApiResponse.success("Create category successfully", category), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a category (admin)")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Update category successfully",
                        categoryService.updateCategory(id, request)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activate or deactivate a category (admin)")
    public ResponseEntity<ApiResponse<CategoryResponse>> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody CategoryStatusRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Change category status successfully",
                        categoryService.changeStatus(id, request.getStatus())));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category (admin)")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Delete category successfully", null));
    }
}