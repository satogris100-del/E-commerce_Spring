package com.booot.pos_system_project.dto.request;

import com.booot.pos_system_project.enums.common.ActiveStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryStatusRequest {
    @NotNull(message = "Status is required")
    private ActiveStatus status;
}