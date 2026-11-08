package com.booot.pos_system_project.mapper;

import com.booot.pos_system_project.dto.response.InventoryMovementResponse;
import com.booot.pos_system_project.model.InventoryMovement;
import com.booot.pos_system_project.model.Product;
import com.booot.pos_system_project.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class InventoryMovementMapper {
    private InventoryMovementMapper() {}

    public static InventoryMovementResponse toResponse(InventoryMovement m) {
        if (m == null) return null;
        Product p = m.getProduct();
        User u = m.getCreatedBy();

        return InventoryMovementResponse.builder()
                .id(m.getId())
                .productId(p == null ? null : p.getId())
                .productName(p == null ? null : p.getName())
                .sku(p == null ? null : p.getSku())
                .type(m.getType())
                .quantity(m.getQuantity())
                .stockBefore(m.getStockBefore())
                .stockAfter(m.getStockAfter())
                .referenceType(m.getReferenceType())
                .referenceId(m.getReferenceId())
                .note(m.getNote())
                .createdById(u == null ? null : u.getId())
                .createdByName(u == null ? null : u.getDisplayName())
                .createdAt(m.getCreatedAt())
                .build();
    }

    public static List<InventoryMovementResponse> toResponseList(Collection<InventoryMovement> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(InventoryMovementMapper::toResponse).toList();
    }
}