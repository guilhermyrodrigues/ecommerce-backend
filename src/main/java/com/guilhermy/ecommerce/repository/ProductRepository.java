package com.guilhermy.ecommerce.repository;

import com.guilhermy.ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByCategoryId(Long categoryId);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByCategoryIdAndNameContainingIgnoreCase(@Param("categoryId") Long categoryId, @Param("name") String name);
    
    List<Product> findByStockGreaterThan(Integer stock);
} 