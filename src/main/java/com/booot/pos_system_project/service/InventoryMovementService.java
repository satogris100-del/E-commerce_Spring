package com.booot.pos_system_project.service;

import com.booot.pos_system_project.dto.request.MovementSearchRequest;
import com.booot.pos_system_project.dto.request.StockMovementRequest;
import com.booot.pos_system_project.dto.response.InventoryMovementResponse;
import com.booot.pos_system_project.enums.product.ReferenceType;
import com.booot.pos_system_project.enums.product.StockInventoryMovementType;
import com.booot.pos_system_project.model.InventoryMovement;
import com.booot.pos_system_project.model.Product;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface InventoryMovementService {

    InventoryMovementResponse createMovement(StockMovementRequest request);

    // Used by other services (checkout, return). Product must already be loaded.
    InventoryMovement record(Product product, StockInventoryMovementType type, int quantity,
                             ReferenceType refType, Long refId, String note);

    Page<InventoryMovementResponse> getMovementsByProduct(Long productId, Pageable pageable);
    Page<InventoryMovementResponse> searchMovements(MovementSearchRequest filter, Pageable pageable);
    InventoryMovementResponse getMovementById(Long id);
}
