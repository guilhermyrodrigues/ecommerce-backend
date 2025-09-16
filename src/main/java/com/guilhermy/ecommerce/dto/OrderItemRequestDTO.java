package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para criação de itens de pedido")
public class OrderItemRequestDTO {
    
    @NotNull(message = "ID do produto é obrigatório")
    @Schema(description = "ID do produto", example = "1")
    private Long productId;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    @Schema(description = "Quantidade do produto", example = "2")
    private Integer quantity;
} 