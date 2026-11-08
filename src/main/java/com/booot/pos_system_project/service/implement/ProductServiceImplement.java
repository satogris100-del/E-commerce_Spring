package com.booot.pos_system_project.service.implement;

import com.booot.pos_system_project.dto.request.ProductRequest;
import com.booot.pos_system_project.dto.response.ProductResponse;
import com.booot.pos_system_project.enums.product.ProductStatus;
import com.booot.pos_system_project.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class ProductServiceImplement implements ProductService {

    @Override
    public ProductResponse createProduct(ProductRequest productRequest, List<MultipartFile> images) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        return null;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return null;
    }

    @Override
    public Page<ProductResponse> searchProducts(String keyword, Long categoryId, ProductStatus status, Pageable pageable) {
        return null;
    }

    @Override
    public ProductResponse changeStatus(Long id, ProductStatus status) {
        return null;
    }

    @Override
    public ProductResponse adjustStock(Long id, Integer quantity, String note) {
        return null;
    }

    @Override
    public List<ProductResponse> getLowStockProducts() {
        return List.of();
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public ProductResponse addImages(Long productId, List<MultipartFile> images) {
        return null;
    }

    @Override
    public ProductResponse deleteImages(Long productId, Long imageId) {
        return null;
    }

    @Override
    public ProductResponse setPrimaryImage(Long productId, Long imageId) {
        return null;
    }

    @Override
    public ProductResponse getProductBySlug(String slug) {
        return null;
    }

    @Override
    public ProductResponse getProductByBarcode(String barcode) {
        return null;
    }

    @Override
    public Page<ProductResponse> getPublicProducts(Long categoryId, Pageable pageable) {
        return null;
    }
}
