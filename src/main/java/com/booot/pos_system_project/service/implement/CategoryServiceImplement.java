package com.booot.pos_system_project.service.implement;

import com.booot.pos_system_project.dto.request.CategoryRequest;
import com.booot.pos_system_project.dto.response.CategoryResponse;
import com.booot.pos_system_project.dto.response.CategoryTreeResponse;
import com.booot.pos_system_project.enums.product.ProductStatus;
import com.booot.pos_system_project.enums.common.ActiveStatus;
import com.booot.pos_system_project.exception.BadRequestException;
import com.booot.pos_system_project.exception.ResourceNotFoundException;
import com.booot.pos_system_project.mapper.CategoryMapper;
import com.booot.pos_system_project.model.Category;
import com.booot.pos_system_project.repository.CategoryRepository;
import com.booot.pos_system_project.repository.ProductRepository;
import com.booot.pos_system_project.service.CategoryService;
import com.booot.pos_system_project.utils.SlugUtil;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplement implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImplement(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        String name = request.getName().trim();
        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(()-> new ResourceNotFoundException("Parent category not found" + request.getParentId()));
        }
        boolean exists = (parent == null) ? categoryRepository.existsByNameIgnoreCaseAndParentIsNull(name)
                : categoryRepository.existsByNameIgnoreCaseAndParentId(name, parent.getId());

        if(exists){
            throw new DuplicateRequestException("Category with name " + name + " already exists");
        }
        Category category = CategoryMapper.toEntity(request);
        category.setParent(parent);
        category.setSlug(makeSlug(name));

        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found" + id));

        String name = request.getName().trim();

        Category parent = null;
        if(request.getParentId() != null){
            if(request.getParentId().equals(id)){
                throw new BadRequestException("A category cannot be its own parent");
            }
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(()-> new ResourceNotFoundException("Parent category not found" + request.getParentId()));

            for(Category child = parent.getParent(); child != null; child = child.getParent()){
                if(child.getId().equals(id)){
                    throw new BadRequestException("Parent cannot be a child of this category");
                }
            }
        }
        boolean exists = (parent == null)
                ? categoryRepository.existsByNameIgnoreCaseAndParentIsNullAndIdNot(name, id)
                : categoryRepository.existsByNameIgnoreCaseAndParentIdAndIdNot(name, parent.getId(), id);
        if(exists){
            throw new DuplicateRequestException("Category with name " + name + " already exists");
        }
        boolean nameChanged = !category.getName().equals(name);
        CategoryMapper.apply(category, request);
        category.setParent(parent);
        if(nameChanged){
            category.setSlug(makeSlug(name));
        }

        return CategoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category not found" + id)
        );

        return CategoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        Sort sort = Sort.by("sortOrder")
                .ascending().and(Sort.by("name").ascending());

        return CategoryMapper.toResponseList(categoryRepository.findAll(sort));
    }

    @Override
    public List<CategoryTreeResponse> getAllCategoriesTree(){
        return categoryRepository.findByParentIsNullAndStatusOrderBySortOrderAsc(ActiveStatus.ACTIVE)
                .stream().map(CategoryMapper::toTree)
                .toList();
    }

    @Override
    public CategoryResponse changeStatus(Long id, ActiveStatus status) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found" + id));
        category.setStatus(status);
        return CategoryMapper.toResponse(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found" + id));
        if(categoryRepository.existsByParentId(id)){
            throw new BadRequestException("Category with id " + id + " already exists");
        }
        if(productRepository.existsByCategoryIdAndStatusNot(id, ProductStatus.DELETED)){
            throw new BadRequestException("Category with id " + id + " has been deleted");

        }
        categoryRepository.delete(category);
    }
    private String makeSlug(String name){
        String base = SlugUtil.toSlug(name);
        String slug = base;
        Integer i = 2;
        while(categoryRepository.existsBySlug(slug)){
            slug = base + "-" +i++;
        }
        return slug;
    }
}
