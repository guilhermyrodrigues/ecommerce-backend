package com.guilhermy.ecommerce.service;

import com.guilhermy.ecommerce.domain.Order;
import com.guilhermy.ecommerce.domain.OrderItem;
import com.guilhermy.ecommerce.domain.Product;
import com.guilhermy.ecommerce.domain.User;
import com.guilhermy.ecommerce.dto.OrderItemRequestDTO;
import com.guilhermy.ecommerce.dto.OrderRequestDTO;
import com.guilhermy.ecommerce.dto.OrderResponseDTO;
import com.guilhermy.ecommerce.enums.OrderStatus;
import com.guilhermy.ecommerce.exception.ResourceNotFoundException;
import com.guilhermy.ecommerce.mapper.OrderMapper;
import com.guilhermy.ecommerce.repository.OrderRepository;
import com.guilhermy.ecommerce.repository.ProductRepository;
import com.guilhermy.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final OrderMapper orderMapper;
    
    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        
        // Criar itens do pedido
        List<OrderItem> orderItems = requestDTO.getItems().stream()
                .map(itemDTO -> createOrderItem(itemDTO, order))
                .collect(Collectors.toList());
        
        order.setItems(orderItems);
        
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(savedOrder);
    }
    
    private OrderItem createOrderItem(OrderItemRequestDTO itemDTO, Order order) {
        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
        // Verificar estoque
        if (product.getStock() < itemDTO.getQuantity()) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + product.getName());
        }
        
        // Atualizar estoque
        productService.updateStock(product.getId(), itemDTO.getQuantity());
        
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(itemDTO.getQuantity());
        
        return orderItem;
    }
    
    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        return orderMapper.toResponseDTO(order);
    }
    
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findByUserId(Long userId) {
        return orderRepository.findByUserIdWithItems(userId).stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    public OrderResponseDTO updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponseDTO(updatedOrder);
    }
    
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado");
        }
        orderRepository.deleteById(id);
    }
} 