package com.guilhermy.ecommerce.dto;

import com.guilhermy.ecommerce.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO de resposta para pedidos")
public class OrderResponseDTO {
    @Schema(description = "ID único do pedido", example = "1")
    private Long id;
    
    @Schema(description = "Data e hora da criação do pedido", example = "2024-01-15T10:30:00Z")
    private Instant moment;
    
    @Schema(description = "Status atual do pedido", example = "PENDING")
    private OrderStatus status;
    
    @Schema(description = "Usuário que fez o pedido")
    private UserResponseDTO user;
    
    @Schema(description = "Lista de itens do pedido")
    private List<OrderItemResponseDTO> items;
    
    @Schema(description = "Valor total do pedido", example = "1299.99")
    private BigDecimal total;
} 