package com.booot.pos_system_project.model;

import com.booot.pos_system_project.enums.product.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_tbl",
        indexes = {
                @Index(name = "idx_product_status", columnList = "status"),
                @Index(name = "idx_product_category", columnList = "category_id")
        })
@Getter
@Setter
@NoArgsConstructor
public class Product extends Base {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String sku;

    @Column(unique = true, length = 100)
    private String barcode;

    @Column(nullable = false, unique = true)
    private String slug;

    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Column(nullable = false)
    private Integer stockQty = 0;

    @Column(nullable = false)
    private Integer lowStockThreshold = 5;

    // Primary image URL (denormalized copy for list pages)
    @Column(length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProductStatus status = ProductStatus.DRAFT;

    @Column(nullable = false)
    private boolean featured = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<ProductImage> images = new ArrayList<>();

    @Version
    private Long version;

    // ---- helpers ----

    public void addImage(ProductImage image) {
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(ProductImage image) {
        images.remove(image);
        image.setProduct(null);
    }

    @Transient
    public boolean isLowStock() {
        return stockQty != null && stockQty <= lowStockThreshold;
    }

    @Transient
    public boolean isSellable() {
        return status == ProductStatus.ACTIVE && stockQty != null && stockQty > 0;
    }

    @Transient
    public BigDecimal getEffectivePrice() {
        return salePrice != null ? salePrice : price;
    }
}