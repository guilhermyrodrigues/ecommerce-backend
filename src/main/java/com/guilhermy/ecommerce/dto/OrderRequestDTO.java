package com.guilhermy.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    
    @NotEmpty(message = "Pedido deve conter pelo menos um item")
    @Valid
    private List<OrderItemRequestDTO> items;
} 