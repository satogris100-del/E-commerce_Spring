package com.booot.pos_system_project.repository;

import com.booot.pos_system_project.enums.common.ActiveStatus;
import com.booot.pos_system_project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByIdAndStatusNot(Long id, ActiveStatus status);
    List<Category> findByParentIsNullAndStatusOrderBySortOrderAsc(ActiveStatus status);
    boolean existsBySlug(String slug);
    boolean existsByParentId(Long parentId);
    boolean existsByNameIgnoreCaseAndParentIsNull(String name);
    boolean existsByNameIgnoreCaseAndParentId(String name, Long parentId);
    boolean existsByNameIgnoreCaseAndParentIsNullAndIdNot(String name, Long id);
    boolean existsByNameIgnoreCaseAndParentIdAndIdNot(String name, Long parentId, Long id);
}

