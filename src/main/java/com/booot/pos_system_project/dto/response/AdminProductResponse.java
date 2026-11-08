package com.booot.pos_system_project.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdminProductResponse extends ProductResponse {
    private BigDecimal costPrice;
    private BigDecimal profitPerUnit;     // effectivePrice - costPrice
    private Long version;                 // useful for optimistic-lock handling on the client
}