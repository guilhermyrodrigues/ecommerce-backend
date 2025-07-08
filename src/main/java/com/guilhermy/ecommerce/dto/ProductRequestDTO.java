package com.guilhermy.ecommerce.dto;

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
public class ProductRequestDTO {
    
    @NotBlank(message = "Nome do produto é obrigatório")
    private String name;
    
    @NotBlank(message = "Descrição do produto é obrigatória")
    private String description;
    
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    private BigDecimal price;
    
    @NotNull(message = "Estoque é obrigatório")
    @PositiveOrZero(message = "Estoque deve ser zero ou maior")
    private Integer stock;
    
    @NotNull(message = "Categoria é obrigatória")
    private Long categoryId;
} 