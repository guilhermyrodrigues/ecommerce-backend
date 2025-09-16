package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de resposta para categorias")
public class CategoryResponseDTO {
    @Schema(description = "ID único da categoria", example = "1")
    private Long id;
    
    @Schema(description = "Nome da categoria", example = "Eletrônicos")
    private String name;
} 