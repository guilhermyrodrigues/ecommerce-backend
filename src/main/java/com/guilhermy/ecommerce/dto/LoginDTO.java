package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para login de usuário")
public class LoginDTO {
    @Schema(description = "Email do usuário", example = "usuario@example.com")
    private String email;
    
    @Schema(description = "Senha do usuário", example = "senha123")
    private String password;
}