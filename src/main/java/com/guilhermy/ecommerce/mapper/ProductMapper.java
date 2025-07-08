package com.guilhermy.ecommerce.mapper;

import com.guilhermy.ecommerce.domain.Product;
import com.guilhermy.ecommerce.dto.ProductRequestDTO;
import com.guilhermy.ecommerce.dto.ProductResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    
    public Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        return product;
    }
    
    public ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        
        if (product.getCategory() != null) {
            dto.setCategory(new CategoryMapper().toResponseDTO(product.getCategory()));
        }
        
        return dto;
    }
} 