package com.booot.pos_system_project.repository;

import com.booot.pos_system_project.enums.product.StockInventoryMovementType;
import com.booot.pos_system_project.model.InventoryMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    Page<InventoryMovement> findByProductId(Long productId, Pageable pageable);

    Page<InventoryMovement> findByProductIdAndType(Long productId,
                                                   StockInventoryMovementType type,
                                                   Pageable pageable);

    Page<InventoryMovement> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to,
                                                   Pageable pageable);
}
