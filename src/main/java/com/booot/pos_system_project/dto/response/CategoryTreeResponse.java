package com.booot.pos_system_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryTreeResponse {
    private Long id;
    private String name;
    private String slug;
    private String imageUrl;
    private List<CategoryTreeResponse> children;
}
