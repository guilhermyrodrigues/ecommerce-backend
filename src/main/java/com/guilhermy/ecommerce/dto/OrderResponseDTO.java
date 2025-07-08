package com.guilhermy.ecommerce.dto;

import com.guilhermy.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Instant moment;
    private OrderStatus status;
    private UserResponseDTO user;
    private List<OrderItemResponseDTO> items;
    private BigDecimal total;
} 