package com.guilhermy.ecommerce.controller;

import com.guilhermy.ecommerce.dto.OrderRequestDTO;
import com.guilhermy.ecommerce.dto.OrderResponseDTO;
import com.guilhermy.ecommerce.enums.OrderStatus;
import com.guilhermy.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO requestDTO) {
        // TODO: Extrair userId do token JWT quando implementar autenticação completa
        // Por enquanto, vamos usar um userId fixo para teste
        Long userId = 1L; // Isso deve vir do token JWT
        
        OrderResponseDTO response = orderService.createOrder(requestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        OrderResponseDTO response = orderService.findById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        List<OrderResponseDTO> response = orderService.findAll();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponseDTO>> findMyOrders() {
        // TODO: Extrair userId do token JWT quando implementar autenticação completa
        Long userId = 1L; // Isso deve vir do token JWT
        
        List<OrderResponseDTO> response = orderService.findByUserId(userId);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        OrderResponseDTO response = orderService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 