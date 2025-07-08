package com.guilhermy.ecommerce.mapper;

import com.guilhermy.ecommerce.domain.OrderItem;
import com.guilhermy.ecommerce.dto.OrderItemResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    
    private final ProductMapper productMapper;
    
    public OrderItemMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    
    public OrderItemResponseDTO toResponseDTO(OrderItem orderItem) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setSubTotal(orderItem.getSubTotal());
        
        if (orderItem.getProduct() != null) {
            dto.setProduct(productMapper.toResponseDTO(orderItem.getProduct()));
        }
        
        return dto;
    }
} 