package com.guilhermy.ecommerce.dto;

import com.guilhermy.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCriadoEventDTO {
    private Long orderId;
    private Long userId;
    private OrderStatus status;
    private Instant moment;
    private List<OrderItemEventDTO> items;
    private Double totalAmount;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemEventDTO {
        private Long productId;
        private String productName;
        private Integer quantity;
        private Double price;
    }
} 