package com.booot.pos_system_project.dto.response;

import com.booot.pos_system_project.enums.product.ProductStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String sku;
    private String barcode;
    private String slug;
    private String shortDescription;
    private String description;
    private String imageUrl;

    private BigDecimal price;
    private BigDecimal salePrice;
    private BigDecimal effectivePrice;   // salePrice if set, otherwise price

    private Integer stockQty;
    private Integer lowStockThreshold;
    private boolean lowStock;
    private boolean sellable;

    private ProductStatus status;
    private boolean featured;

    private Long categoryId;
    private String categoryName;

    private List<ProductImageResponse> images;   // only on detail endpoints

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}