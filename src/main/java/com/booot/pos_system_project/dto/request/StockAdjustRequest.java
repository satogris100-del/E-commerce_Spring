package com.booot.pos_system_project.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockAdjustRequest {

    @NotNull(message = "Quantity is required")
    private Integer quantity;
    @Size(max = 255)
    private String note;
}
