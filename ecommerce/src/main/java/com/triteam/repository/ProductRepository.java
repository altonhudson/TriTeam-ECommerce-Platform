package com.triteam.repository;

import com.triteam.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Finds products filtered by category and price limit with pagination + sorting
    Page<Product> findByCategoryAndUnitPriceLessThanEqual(String category, Double unitPrice, Pageable pageable);

    // Finds products filtered only by price limit with pagination + sorting)
    Page<Product> findByUnitPriceLessThanEqual(Double unitPrice, Pageable pageable);
}