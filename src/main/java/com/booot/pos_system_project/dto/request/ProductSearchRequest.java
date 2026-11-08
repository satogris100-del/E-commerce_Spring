package com.booot.pos_system_project.dto.request;

import com.booot.pos_system_project.enums.product.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSearchRequest {
    private String keyword;            // name, sku or barcode
    private Long categoryId;
    private ProductStatus status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean featured;
    private Boolean lowStockOnly;
}