package com.booot.pos_system_project.service;

import com.booot.pos_system_project.dto.request.ProductRequest;
import com.booot.pos_system_project.dto.response.ProductResponse;
import com.booot.pos_system_project.enums.product.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest, List<MultipartFile> images);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    ProductResponse getProductById(Long id);
    Page<ProductResponse> searchProducts(String keyword, Long categoryId,
                                         ProductStatus status, Pageable pageable);

    ProductResponse changeStatus(Long id, ProductStatus status);
    ProductResponse adjustStock(Long id, Integer quantity, String note);
    List<ProductResponse> getLowStockProducts();
    void deleteProduct(Long id);

    // Images
    ProductResponse addImages(Long productId, List<MultipartFile> images);
    ProductResponse deleteImages(Long productId, Long imageId);
    ProductResponse setPrimaryImage(Long productId, Long imageId);

    // Public / POS
    ProductResponse getProductBySlug(String slug);
    ProductResponse getProductByBarcode(String barcode);
    Page<ProductResponse> getPublicProducts(Long categoryId, Pageable pageable);
}
