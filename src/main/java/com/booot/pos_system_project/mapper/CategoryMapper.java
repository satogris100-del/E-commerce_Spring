package com.booot.pos_system_project.mapper;

import com.booot.pos_system_project.dto.request.CategoryRequest;
import com.booot.pos_system_project.dto.response.CategoryResponse;
import com.booot.pos_system_project.dto.response.CategoryTreeResponse;
import com.booot.pos_system_project.enums.common.ActiveStatus;
import com.booot.pos_system_project.model.Category;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public final class CategoryMapper {
    private CategoryMapper() {}

    public static Category toEntity(CategoryRequest request) {
        if (request == null) return null;
        Category c = new Category();
        apply(c, request);
        return c;
    }

    // parent and slug are set in the service
    public static void apply(Category c, CategoryRequest r) {
        c.setName(r.getName().trim());
        c.setDescription(blankToNull(r.getDescription()));
        c.setImageUrl(blankToNull(r.getImageUrl()));
        c.setSortOrder(r.getSortOrder() == null ? 0 : r.getSortOrder());
        if (r.getStatus() != null) c.setStatus(r.getStatus());
    }

    public static CategoryResponse toResponse(Category c) {
        if (c == null) return null;
        return CategoryResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .slug(c.getSlug())
                .description(c.getDescription())
                .imageUrl(c.getImageUrl())
                .sortOrder(c.getSortOrder())
                .status(c.getStatus())
                .parentId(c.getParent() == null ? null : c.getParent().getId())
                .parentName(c.getParent() == null ? null : c.getParent().getName())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }

    public static List<CategoryResponse> toResponseList(Collection<Category> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(CategoryMapper::toResponse).toList();
    }

    // Only ACTIVE children are shown in the tree
    public static CategoryTreeResponse toTree(Category c) {
        if (c == null) return null;
        return CategoryTreeResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .slug(c.getSlug())
                .imageUrl(c.getImageUrl())
                .children(c.getChildren().stream()
                        .filter(ch -> ch.getStatus() == ActiveStatus.ACTIVE)
                        .map(CategoryMapper::toTree)
                        .toList())
                .build();
    }

    private static String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}