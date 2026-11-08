package com.booot.pos_system_project.dto.request;

import com.booot.pos_system_project.enums.product.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Name must be at most 255 characters")
    private String name;

    @NotBlank(message = "SKU is required")
    @Size(max = 100, message = "SKU must be at most 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "SKU may only contain letters, digits, '.', '_' and '-'")
    private String sku;

    @Size(max = 100, message = "Barcode must be at most 100 characters")
    private String barcode;

    @Size(max = 255, message = "Short description must be at most 255 characters")
    private String shortDescription;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price allows up to 10 digits and 2 decimals")
    private BigDecimal price;

    @DecimalMin(value = "0.00", message = "Sale price cannot be negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal salePrice;

    @DecimalMin(value = "0.00", message = "Cost price cannot be negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal costPrice;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stockQty = 0;

    @NotNull
    @Min(value = 0, message = "Low stock threshold cannot be negative")
    private Integer lowStockThreshold = 5;

    private boolean featured = false;

    private ProductStatus status = ProductStatus.DRAFT;

    @NotNull(message = "Category is required")
    private Long categoryId;
}