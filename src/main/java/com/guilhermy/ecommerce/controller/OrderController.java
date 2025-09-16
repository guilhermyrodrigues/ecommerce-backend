package com.guilhermy.ecommerce.controller;

import com.guilhermy.ecommerce.dto.OrderRequestDTO;
import com.guilhermy.ecommerce.dto.OrderResponseDTO;
import com.guilhermy.ecommerce.enums.OrderStatus;
import com.guilhermy.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO requestDTO) {
        // TODO: Extrair userId do token JWT quando implementar autenticação completa
        // Por enquanto, vamos usar um userId fixo para teste
        Long userId = 1L; // Isso deve vir do token JWT
        
        OrderResponseDTO response = orderService.createOrder(requestDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<OrderResponseDTO> findById(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        OrderResponseDTO response = orderService.findById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Lista todos os pedidos do sistema (apenas para administradores)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        List<OrderResponseDTO> response = orderService.findAll();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/my-orders")
    @Operation(summary = "Listar meus pedidos", description = "Lista os pedidos do usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos do usuário retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<OrderResponseDTO>> findMyOrders() {
        // TODO: Extrair userId do token JWT quando implementar autenticação completa
        Long userId = 1L; // Isso deve vir do token JWT
        
        List<OrderResponseDTO> response = orderService.findByUserId(userId);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido fornecido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @Parameter(description = "ID do pedido") @PathVariable Long id,
            @Parameter(description = "Novo status do pedido") @RequestParam OrderStatus status) {
        OrderResponseDTO response = orderService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pedido", description = "Remove um pedido do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID do pedido") @PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 