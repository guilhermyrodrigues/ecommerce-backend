package com.guilhermy.ecommerce.controller;

import com.guilhermy.ecommerce.dto.ProductRequestDTO;
import com.guilhermy.ecommerce.dto.ProductResponseDTO;
import com.guilhermy.ecommerce.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    
    private final ProductService productService;
    
    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO response = productService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProductResponseDTO> findById(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        ProductResponseDTO response = productService.findById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Listar produtos", description = "Lista produtos com filtros opcionais por categoria e nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    })
    public ResponseEntity<List<ProductResponseDTO>> findAll(
            @Parameter(description = "ID da categoria para filtrar") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Nome do produto para filtrar") @RequestParam(required = false) String name) {
        
        List<ProductResponseDTO> response;
        
        if (categoryId != null && name != null && !name.trim().isEmpty()) {
            response = productService.findByCategoryAndName(categoryId, name);
        } else if (categoryId != null) {
            response = productService.findByCategory(categoryId);
        } else if (name != null && !name.trim().isEmpty()) {
            response = productService.findByName(name);
        } else {
            response = productService.findAll();
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/available")
    @Operation(summary = "Listar produtos disponíveis", description = "Lista apenas produtos com estoque disponível")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos disponíveis retornada com sucesso")
    })
    public ResponseEntity<List<ProductResponseDTO>> findAvailableProducts() {
        List<ProductResponseDTO> response = productService.findAvailableProducts();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<ProductResponseDTO> update(
            @Parameter(description = "ID do produto") @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO response = productService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Remove um produto do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID do produto") @PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 