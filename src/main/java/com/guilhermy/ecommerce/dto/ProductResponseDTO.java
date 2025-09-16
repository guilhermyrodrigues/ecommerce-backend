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
@Schema(description = "DTO de resposta para produtos")
public class ProductResponseDTO {
    @Schema(description = "ID único do produto", example = "1")
    private Long id;
    
    @Schema(description = "Nome do produto", example = "Smartphone Samsung Galaxy")
    private String name;
    
    @Schema(description = "Descrição do produto", example = "Smartphone com tela de 6.1 polegadas, 128GB de armazenamento")
    private String description;
    
    @Schema(description = "Preço do produto", example = "1299.99")
    private BigDecimal price;
    
    @Schema(description = "Quantidade em estoque", example = "50")
    private Integer stock;
    
    @Schema(description = "Categoria do produto")
    private CategoryResponseDTO category;
} 