package com.booot.pos_system_project.dto.request;

import com.booot.pos_system_project.enums.product.StockInventoryMovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockMovementRequest {

    @NotNull(message = "Product is required")
    private Long productId;

    @NotNull(message = "Type is required")
    private StockInventoryMovementType type;

    // IN / OUT / RETURN / SALE: a positive number, the sign is applied by the service
    // ADJUST: the new counted stock (absolute value)
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Size(max = 255, message = "Note must be at most 255 characters")
    private String note;
}