package com.booot.pos_system_project.model;

import com.booot.pos_system_project.enums.product.ReferenceType;
import com.booot.pos_system_project.enums.product.StockInventoryMovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory_movement_tbl",
        indexes = {
                @Index(name = "idx_movement_product", columnList = "product_id"),
                @Index(name = "idx_movement_created", columnList = "created_at")
        })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryMovement extends Base {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StockInventoryMovementType type;

    @Column(nullable = false)
    private Integer quantity;              // + adds stock, - removes stock

    @Column(nullable = false)
    private Integer stockBefore;

    @Column(nullable = false)
    private Integer stockAfter;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ReferenceType referenceType;

    private Long referenceId;              // order id, return id, ...

    @Column(length = 255)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}