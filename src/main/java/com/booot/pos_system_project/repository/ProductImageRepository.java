package com.booot.pos_system_project.repository;

import com.booot.pos_system_project.dto.request.ProductImageReorderRequest;
import com.booot.pos_system_project.dto.response.ProductImageResponse;
import com.booot.pos_system_project.model.Product;
import com.booot.pos_system_project.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductIdOrderBySortOrderAsc(Long productId);

    Optional<ProductImage> findByIdAndProductId(Long id, Long productId);

    long countByProductId(Long productId);

    Optional<ProductImage> findByProductIdAndPrimaryTrue(Long productId);
}