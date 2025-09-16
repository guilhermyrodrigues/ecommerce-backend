package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta com token JWT")
public class JwtResponseDTO {
    @Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    public JwtResponseDTO(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }
}
