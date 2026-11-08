package com.booot.pos_system_project.dto.request;

import com.booot.pos_system_project.enums.product.StockInventoryMovementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovementSearchRequest {
    private Long productId;
    private StockInventoryMovementType type;
    private LocalDate fromDate;
    private LocalDate toDate;
}