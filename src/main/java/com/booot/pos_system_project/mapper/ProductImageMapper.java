package com.booot.pos_system_project.mapper;

import com.booot.pos_system_project.dto.response.ProductImageResponse;
import com.booot.pos_system_project.model.ProductImage;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ProductImageMapper {
    private ProductImageMapper() {}

    public static ProductImageResponse toResponse(ProductImage i) {
        if (i == null) return null;
        return ProductImageResponse.builder()
                .id(i.getId())
                .productId(i.getProduct() == null ? null : i.getProduct().getId())
                .url(i.getUrl())
                .primary(i.isPrimary())
                .sortOrder(i.getSortOrder())
                .build();
    }

    public static List<ProductImageResponse> toResponseList(Collection<ProductImage> list) {
        if (list == null) return Collections.emptyList();
        return list.stream()
                .sorted(Comparator.comparing(ProductImage::getSortOrder,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(ProductImageMapper::toResponse)
                .toList();
    }
}
