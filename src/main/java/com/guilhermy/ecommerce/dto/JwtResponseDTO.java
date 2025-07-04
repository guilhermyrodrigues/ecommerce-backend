package com.guilhermy.ecommerce.dto;

public class JwtResponseDTO {
    private String token;
    public JwtResponseDTO(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
