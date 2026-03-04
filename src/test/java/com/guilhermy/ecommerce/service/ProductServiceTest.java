package com.guilhermy.ecommerce.service;

import com.guilhermy.ecommerce.domain.Product;
import com.guilhermy.ecommerce.exception.ResourceNotFoundException;
import com.guilhermy.ecommerce.mapper.ProductMapper;
import com.guilhermy.ecommerce.repository.CategoryRepository;
import com.guilhermy.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void updateStockShouldDecreaseStockWhenEnoughQuantity() {
        Product product = new Product();
        product.setId(1L);
        product.setStock(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.updateStock(1L, 3);

        assertEquals(7, product.getStock());
        verify(productRepository).save(product);
    }

    @Test
    void updateStockShouldThrowWhenStockIsInsufficient() {
        Product product = new Product();
        product.setId(1L);
        product.setStock(2);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class, () -> productService.updateStock(1L, 5));
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStockShouldThrowWhenProductDoesNotExist() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateStock(99L, 1));
        verify(productRepository, never()).save(any());
    }
}
