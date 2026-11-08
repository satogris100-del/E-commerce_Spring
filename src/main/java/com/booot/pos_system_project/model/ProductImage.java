package com.booot.pos_system_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductImage extends Base {

    @Column(nullable = false, length = 500)
    private String url;

    @Column(nullable = false)
    private String publicId;

    @Column(nullable = false)
    private boolean primary = false;
    @Column(nullable = false)
    private Integer sortOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

}
