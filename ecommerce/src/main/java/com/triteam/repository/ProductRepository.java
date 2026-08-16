package com.triteam.repository;

import com.triteam.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
        // Finds products filtered by category, price limit and a text keyword
        // (case-insensitive)
        Page<Product> findByCategoryAndUnitPriceLessThanEqualAndNameContainingIgnoreCase(String category, Double unitPrice,
                String keyword, Pageable pageable);

        // Finds products filtered by price limit and a text keyword (case-insensitive)
        Page<Product> findByUnitPriceLessThanEqualAndNameContainingIgnoreCase(Double unitPrice, String keyword,
                Pageable pageable);

        // Lookup query for SKU (used when adding a product to match the format of "CATEGORY-Lowest 3 digit integer")
        Optional<Product> findFirstBySkuStartingWithOrderBySkuDesc(String prefix);
}