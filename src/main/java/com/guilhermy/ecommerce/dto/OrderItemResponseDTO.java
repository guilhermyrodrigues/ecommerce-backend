package com.guilhermy.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private ProductResponseDTO product;
    private Integer quantity;
    private BigDecimal subTotal;
} 