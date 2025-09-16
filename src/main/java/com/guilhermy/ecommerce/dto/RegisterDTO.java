package com.guilhermy.ecommerce.dto;

import com.guilhermy.ecommerce.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para registro de novo usuário")
public class RegisterDTO {
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String name;
    
    @Schema(description = "Email do usuário", example = "joao@example.com")
    private String email;
    
    @Schema(description = "Senha do usuário", example = "senha123")
    private String password;
    
    @Schema(description = "Papel/função do usuário no sistema", example = "USER")
    private Role role;
}