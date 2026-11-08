package com.booot.pos_system_project.enums.product;

public enum ReferenceType {
    ORDER,      // stock sold through an order (SALE), or restored by a cancelled order
    RETURN,     // customer return put back into stock
    MANUAL      // staff or admin changed it by hand (restock, damage, stock count)
}