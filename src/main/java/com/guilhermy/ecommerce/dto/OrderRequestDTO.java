package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO para criação de pedidos")
public class OrderRequestDTO {
    
    @NotEmpty(message = "Pedido deve conter pelo menos um item")
    @Valid
    @Schema(description = "Lista de itens do pedido")
    private List<OrderItemRequestDTO> items;
} 