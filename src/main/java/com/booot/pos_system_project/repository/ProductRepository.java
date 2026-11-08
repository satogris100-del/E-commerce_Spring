package com.booot.pos_system_project.repository;

import com.booot.pos_system_project.enums.product.ProductStatus;
import com.booot.pos_system_project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    // ----- single product -----
    Optional<Product> findByIdAndStatusNot(Long id, ProductStatus status);          // added
    Optional<Product> findBySlugAndStatus(String slug, ProductStatus status);
    Optional<Product> findByBarcodeAndStatus(String barcode, ProductStatus status);
    Optional<Product> findByBarcode(String barcode);

    // ----- lists -----
    Page<Product> findByCategoryIdAndStatus(Long categoryId, ProductStatus status, Pageable pageable);
    Page<Product> findByStatusNot(ProductStatus status, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.status <> :deleted AND p.stockQty <= p.lowStockThreshold")       // added
    List<Product> findLowStock(@Param("deleted") ProductStatus deleted);

    // ----- duplicate checks -----
    boolean existsBySku(String sku);
    boolean existsBySlug(String slug);
    boolean existsByBarcode(String barcode);
    boolean existsBySkuAndIdNot(String sku, Long id);
    boolean existsByBarcodeAndIdNot(String barcode, Long id);                        // added

    // ----- used by CategoryService -----
    boolean existsByCategoryIdAndStatusNot(Long categoryId, ProductStatus status);
}