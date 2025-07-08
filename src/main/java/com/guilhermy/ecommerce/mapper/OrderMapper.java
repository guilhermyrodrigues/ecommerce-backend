package com.guilhermy.ecommerce.mapper;

import com.guilhermy.ecommerce.domain.Order;
import com.guilhermy.ecommerce.dto.OrderResponseDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    
    private final OrderItemMapper orderItemMapper;
    private final UserMapper userMapper;
    
    public OrderMapper(OrderItemMapper orderItemMapper, UserMapper userMapper) {
        this.orderItemMapper = orderItemMapper;
        this.userMapper = userMapper;
    }
    
    public OrderResponseDTO toResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setMoment(order.getMoment());
        dto.setStatus(order.getStatus());
        dto.setTotal(order.getTotal());
        
        if (order.getUser() != null) {
            dto.setUser(userMapper.toResponseDTO(order.getUser()));
        }
        
        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream()
                    .map(orderItemMapper::toResponseDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
} 