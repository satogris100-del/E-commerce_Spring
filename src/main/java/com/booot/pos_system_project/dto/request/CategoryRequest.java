package com.booot.pos_system_project.dto.request;

import com.booot.pos_system_project.enums.common.ActiveStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 150, message = "Name must be at most 150 characters")
    private String name;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @Size(max = 500)
    private String imageUrl;

    @Min(value = 0, message = "Sort order cannot be negative")
    private Integer sortOrder = 0;

    private ActiveStatus status = ActiveStatus.ACTIVE;

    private Long parentId;
}

