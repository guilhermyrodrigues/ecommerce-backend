package com.guilhermy.ecommerce.mapper;

import com.guilhermy.ecommerce.domain.User;
import com.guilhermy.ecommerce.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
} 