package com.guilhermy.ecommerce.repository;

import com.guilhermy.ecommerce.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query("SELECT o FROM Order o JOIN FETCH o.items i JOIN FETCH i.product WHERE o.user.id = :userId")
    List<Order> findByUserIdWithItems(@Param("userId") Long userId);
    
    List<Order> findByUserId(Long userId);
} 