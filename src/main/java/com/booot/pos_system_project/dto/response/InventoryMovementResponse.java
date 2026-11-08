package com.booot.pos_system_project.dto.response;

import com.booot.pos_system_project.enums.product.ReferenceType;
import com.booot.pos_system_project.enums.product.StockInventoryMovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMovementResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String sku;
    private StockInventoryMovementType type;
    private Integer quantity;          // signed: +10 or -3
    private Integer stockBefore;
    private Integer stockAfter;
    private ReferenceType referenceType;
    private Long referenceId;
    private String note;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
}