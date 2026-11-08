package com.booot.pos_system_project.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageReorderRequest {
    @NotEmpty(message = "Image ids are required")
    private List<Long> imageIds;
}
