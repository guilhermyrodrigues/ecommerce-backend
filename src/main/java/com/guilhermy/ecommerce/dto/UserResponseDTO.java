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
@Schema(description = "DTO de resposta para usuários")
public class UserResponseDTO {
    @Schema(description = "ID único do usuário", example = "1")
    private Long id;
    
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String name;
    
    @Schema(description = "Email do usuário", example = "joao@example.com")
    private String email;
} 