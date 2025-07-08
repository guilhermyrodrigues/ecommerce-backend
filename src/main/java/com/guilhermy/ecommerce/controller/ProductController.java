package com.guilhermy.ecommerce.controller;

import com.guilhermy.ecommerce.dto.ProductRequestDTO;
import com.guilhermy.ecommerce.dto.ProductResponseDTO;
import com.guilhermy.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO response = productService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductResponseDTO response = productService.findById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name) {
        
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
    public ResponseEntity<List<ProductResponseDTO>> findAvailableProducts() {
        List<ProductResponseDTO> response = productService.findAvailableProducts();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO response = productService.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 