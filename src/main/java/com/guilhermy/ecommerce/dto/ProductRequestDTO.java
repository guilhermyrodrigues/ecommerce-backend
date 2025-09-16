package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para criação e atualização de produtos")
public class ProductRequestDTO {
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Schema(description = "Nome do produto", example = "Smartphone Samsung Galaxy")
    private String name;
    
    @NotBlank(message = "Descrição do produto é obrigatória")
    @Schema(description = "Descrição detalhada do produto", example = "Smartphone com tela de 6.1 polegadas, 128GB de armazenamento")
    private String description;
    
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    @Schema(description = "Preço do produto", example = "1299.99")
    private BigDecimal price;
    
    @NotNull(message = "Estoque é obrigatório")
    @PositiveOrZero(message = "Estoque deve ser zero ou maior")
    @Schema(description = "Quantidade em estoque", example = "50")
    private Integer stock;
    
    @NotNull(message = "Categoria é obrigatória")
    @Schema(description = "ID da categoria do produto", example = "1")
    private Long categoryId;
} 