package com.supermarketsystem.supermarket_system_g8_1_03.repository;

import com.supermarketsystem.supermarket_system_g8_1_03.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Add this exact line to fix the error:
    List<Product> findTop6ByOrderByCreatedAtDesc();

    // The other methods we need for the storefront:
    List<Product> findByIsPromotedTrue();
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryId(Long categoryId);
}