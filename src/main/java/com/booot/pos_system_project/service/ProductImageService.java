package com.booot.pos_system_project.service;

import com.booot.pos_system_project.dto.request.ProductImageReorderRequest;
import com.booot.pos_system_project.dto.response.ProductImageResponse;
import com.booot.pos_system_project.model.Product;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductImageService {
    List<ProductImageResponse> getImages(Long productId);

    List<ProductImageResponse> uploadImages(Long productId, List<MultipartFile> files);

    void attachImages(Product product, List<MultipartFile> files);

    List<ProductImageResponse> setPrimary(Long productId, Long imageId);

    List<ProductImageResponse> reorder(Long productId, ProductImageReorderRequest request);

    void deleteImage(Long productId, Long imageId);
}
