package com.booot.pos_system_project.enums.order;

public enum ShipmentStatus {
    PREPARING,          // order is being packed, not handed to the carrier yet
    SHIPPED,            // handed to the carrier
    IN_TRANSIT,         // moving between hubs
    OUT_FOR_DELIVERY,   // courier is delivering today
    DELIVERED,          // received by the customer
    FAILED,             // delivery attempt failed (no one home, wrong address)
    RETURNED;           // sent back to the shop

    public boolean canTransitionTo(ShipmentStatus next) {
        return switch (this) {
            case PREPARING        -> next == SHIPPED;
            case SHIPPED          -> next == IN_TRANSIT || next == OUT_FOR_DELIVERY || next == FAILED;
            case IN_TRANSIT       -> next == OUT_FOR_DELIVERY || next == FAILED;
            case OUT_FOR_DELIVERY -> next == DELIVERED || next == FAILED;
            case FAILED           -> next == OUT_FOR_DELIVERY || next == RETURNED;
            case DELIVERED, RETURNED -> false;
        };
    }
}