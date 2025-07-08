package com.guilhermy.ecommerce.mapper;

import com.guilhermy.ecommerce.domain.Category;
import com.guilhermy.ecommerce.dto.CategoryRequestDTO;
import com.guilhermy.ecommerce.dto.CategoryResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    
    public Category toEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }
    
    public CategoryResponseDTO toResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
} 