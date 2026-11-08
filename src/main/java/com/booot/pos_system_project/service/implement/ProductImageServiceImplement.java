package com.booot.pos_system_project.service.implement;

import com.booot.pos_system_project.config.CloudService;
import com.booot.pos_system_project.dto.request.ProductImageReorderRequest;
import com.booot.pos_system_project.dto.response.ProductImageResponse;
import com.booot.pos_system_project.mapper.ProductImageMapper;
import com.booot.pos_system_project.model.Product;
import com.booot.pos_system_project.repository.ProductImageRepository;
import com.booot.pos_system_project.service.ProductImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductImageServiceImplement implements ProductImageService {
    private static final Logger log = LoggerFactory.getLogger(ProductImageServiceImplement.class);
    private static final int MAX_IMAGES = 8;
    private static final String FOLDER = "pos/products";

    private final ProductImageRepository productImageRepository;
    private final CloudService  cloudService;
    private final ProductImageMapper productImageMapper;
    public ProductImageServiceImplement(ProductImageRepository productImageRepository,
                                        CloudService cloudService,
                                        ProductImageMapper productImageMapper) {
        this.productImageRepository = productImageRepository;
        this.cloudService = cloudService;
        this.productImageMapper = productImageMapper;
    }
    @Override
    public List<ProductImageResponse> getImages(Long productId) {
        return ProductImageMapper.toResponseList(productImageRepository
                .findByProductIdOrderBySortOrderAsc(productId));
    }

    @Override
    public List<ProductImageResponse> uploadImages(Long productId, List<MultipartFile> files) {
        return List.of(

        );
    }

    @Override
    public void attachImages(Product product, List<MultipartFile> files) {

    }

    @Override
    public List<ProductImageResponse> setPrimary(Long productId, Long imageId) {
        return List.of();
    }

    @Override
    public List<ProductImageResponse> reorder(Long productId, ProductImageReorderRequest request) {
        return List.of();
    }

    @Override
    public void deleteImage(Long productId, Long imageId) {

    }
}
