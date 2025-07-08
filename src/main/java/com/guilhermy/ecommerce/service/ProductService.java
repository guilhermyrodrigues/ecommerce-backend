package com.guilhermy.ecommerce.service;

import com.guilhermy.ecommerce.domain.Category;
import com.guilhermy.ecommerce.domain.Product;
import com.guilhermy.ecommerce.dto.ProductRequestDTO;
import com.guilhermy.ecommerce.dto.ProductResponseDTO;
import com.guilhermy.ecommerce.exception.ResourceNotFoundException;
import com.guilhermy.ecommerce.mapper.ProductMapper;
import com.guilhermy.ecommerce.repository.CategoryRepository;
import com.guilhermy.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    
    public ProductResponseDTO create(ProductRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        Product product = productMapper.toEntity(requestDTO);
        product.setCategory(category);
        
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }
    
    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return productMapper.toResponseDTO(product);
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findByCategoryAndName(Long categoryId, String name) {
        return productRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, name).stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAvailableProducts() {
        return productRepository.findByStockGreaterThan(0).stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    public ProductResponseDTO update(Long id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setCategory(category);
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(updatedProduct);
    }
    
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        productRepository.deleteById(id);
    }
    
    public void updateStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        
        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        
        product.setStock(newStock);
        productRepository.save(product);
    }
} 