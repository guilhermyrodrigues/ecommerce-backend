package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de resposta para itens de pedido")
public class OrderItemResponseDTO {
    @Schema(description = "ID único do item", example = "1")
    private Long id;
    
    @Schema(description = "Produto do item")
    private ProductResponseDTO product;
    
    @Schema(description = "Quantidade do produto", example = "2")
    private Integer quantity;
    
    @Schema(description = "Subtotal do item (preço × quantidade)", example = "2599.98")
    private BigDecimal subTotal;
} 