package com.booot.pos_system_project.mapper;

import com.booot.pos_system_project.dto.request.ProductRequest;
import com.booot.pos_system_project.dto.response.AdminProductResponse;
import com.booot.pos_system_project.dto.response.ProductImageResponse;
import com.booot.pos_system_project.dto.response.ProductResponse;
import com.booot.pos_system_project.dto.response.ProductSummaryResponse;
import com.booot.pos_system_project.model.Product;
import com.booot.pos_system_project.model.ProductImage;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public final class ProductMapper {

    private ProductMapper() {}

    // =====================================================
    // Request -> Entity
    // =====================================================

    /** New product. Category and slug are set later in the service. */
    public static Product toEntity(ProductRequest request) {
        if (request == null) return null;
        Product product = new Product();
        apply(product, request);
        return product;
    }

    /** Copies request fields onto an existing entity (used by create and update). */
    public static void apply(Product product, ProductRequest request) {
        if (product == null || request == null) return;

        product.setName(request.getName().trim());
        product.setSku(request.getSku().trim());
        product.setBarcode(blankToNull(request.getBarcode()));   // unique column: "" must become null
        product.setShortDescription(blankToNull(request.getShortDescription()));
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSalePrice(request.getSalePrice());
        product.setCostPrice(request.getCostPrice());
        product.setStockQty(request.getStockQty() == null ? 0 : request.getStockQty());
        product.setLowStockThreshold(
                request.getLowStockThreshold() == null ? 5 : request.getLowStockThreshold());
        product.setFeatured(request.isFeatured());

        if (request.getStatus() != null) {
            product.setStatus(request.getStatus());
        }
        // Not copied on purpose: id, slug, imageUrl, images, category, version
    }

    // =====================================================
    // Entity -> Response
    // =====================================================

    /** List pages: no images collection, so no extra queries. */
    public static ProductResponse toResponse(Product p) {
        if (p == null) return null;
        return base(p).build();
    }

    /** Detail page: includes all images. */
    public static ProductResponse toDetailResponse(Product p) {
        if (p == null) return null;
        return base(p)
                .images(toImageResponseList(p.getImages()))
                .build();
    }

    /** ADMIN only: includes cost price and profit. */
    public static AdminProductResponse toAdminResponse(Product p) {
        if (p == null) return null;

        return AdminProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .sku(p.getSku())
                .barcode(p.getBarcode())
                .slug(p.getSlug())
                .shortDescription(p.getShortDescription())
                .description(p.getDescription())
                .imageUrl(p.getImageUrl())
                .price(p.getPrice())
                .salePrice(p.getSalePrice())
                .effectivePrice(p.getEffectivePrice())
                .stockQty(p.getStockQty())
                .lowStockThreshold(p.getLowStockThreshold())
                .lowStock(p.isLowStock())
                .sellable(p.isSellable())
                .status(p.getStatus())
                .featured(p.isFeatured())
                .categoryId(categoryId(p))
                .categoryName(categoryName(p))
                .images(toImageResponseList(p.getImages()))
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .costPrice(p.getCostPrice())
                .profitPerUnit(profit(p))
                .version(p.getVersion())
                .build();
    }

    /** Small payload for the POS grid, cart and dropdowns. */
    public static ProductSummaryResponse toSummary(Product p) {
        if (p == null) return null;
        return ProductSummaryResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .sku(p.getSku())
                .barcode(p.getBarcode())
                .imageUrl(p.getImageUrl())
                .effectivePrice(p.getEffectivePrice())
                .stockQty(p.getStockQty())
                .sellable(p.isSellable())
                .build();
    }

    // =====================================================
    // Images
    // =====================================================

    public static ProductImageResponse toImageResponse(ProductImage image) {
        if (image == null) return null;
        return ProductImageResponse.builder()
                .id(image.getId())
                .url(image.getUrl())
                .primary(image.isPrimary())
                .sortOrder(image.getSortOrder())
                .build();
    }

    public static List<ProductImageResponse> toImageResponseList(Collection<ProductImage> images) {
        if (images == null) return Collections.emptyList();
        return images.stream().map(ProductMapper::toImageResponse).toList();
    }

    // =====================================================
    // Lists
    // =====================================================

    public static List<ProductResponse> toResponseList(Collection<Product> products) {
        if (products == null) return Collections.emptyList();
        return products.stream().map(ProductMapper::toResponse).toList();
    }

    public static List<ProductSummaryResponse> toSummaryList(Collection<Product> products) {
        if (products == null) return Collections.emptyList();
        return products.stream().map(ProductMapper::toSummary).toList();
    }

    // =====================================================
    // Private helpers
    // =====================================================

    private static ProductResponse.ProductResponseBuilder<?, ?> base(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .sku(p.getSku())
                .barcode(p.getBarcode())
                .slug(p.getSlug())
                .shortDescription(p.getShortDescription())
                .description(p.getDescription())
                .imageUrl(p.getImageUrl())
                .price(p.getPrice())
                .salePrice(p.getSalePrice())
                .effectivePrice(p.getEffectivePrice())
                .stockQty(p.getStockQty())
                .lowStockThreshold(p.getLowStockThreshold())
                .lowStock(p.isLowStock())
                .sellable(p.isSellable())
                .status(p.getStatus())
                .featured(p.isFeatured())
                .categoryId(categoryId(p))
                .categoryName(categoryName(p))
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt());
    }

    private static BigDecimal profit(Product p) {
        if (p.getCostPrice() == null || p.getEffectivePrice() == null) return null;
        return p.getEffectivePrice().subtract(p.getCostPrice());
    }

    private static Long categoryId(Product p) {
        return p.getCategory() == null ? null : p.getCategory().getId();
    }

    private static String categoryName(Product p) {
        return p.getCategory() == null ? null : p.getCategory().getName();
    }

    private static String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}