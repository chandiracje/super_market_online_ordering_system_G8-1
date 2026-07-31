package com.supermarketsystem.supermarket_system_g8_1_03.repository;

import com.supermarketsystem.supermarket_system_g8_1_03.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentCategoryIsNull();
}