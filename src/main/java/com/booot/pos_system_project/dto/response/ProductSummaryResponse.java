package com.booot.pos_system_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductSummaryResponse {
    private Long id;
    private String name;
    private String sku;
    private String barcode;
    private String imageUrl;
    private BigDecimal effectivePrice;
    private Integer stockQty;
    private boolean sellable;
}