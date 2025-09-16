package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para criação e atualização de categorias")
public class CategoryRequestDTO {
    
    @NotBlank(message = "Nome da categoria é obrigatório")
    @Schema(description = "Nome da categoria", example = "Eletrônicos")
    private String name;
} 