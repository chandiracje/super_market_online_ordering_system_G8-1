package com.supermarketsystem.supermarket_system_g8_1_03.service;

import com.supermarketsystem.supermarket_system_g8_1_03.models.Category;
import com.supermarketsystem.supermarket_system_g8_1_03.models.Product;
import com.supermarketsystem.supermarket_system_g8_1_03.repository.CategoryRepository;
import com.supermarketsystem.supermarket_system_g8_1_03.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorefrontService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getRootCategories() {
        return categoryRepository.findByParentCategoryIsNull();
    }

    public List<Product> getNewestArrivals() {
        return productRepository.findTop6ByOrderByCreatedAtDesc();
    }

    public List<Product> getPromotedProducts() {
        return productRepository.findByIsPromotedTrue();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}