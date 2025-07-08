package com.guilhermy.ecommerce.service;

import com.guilhermy.ecommerce.domain.Category;
import com.guilhermy.ecommerce.dto.CategoryRequestDTO;
import com.guilhermy.ecommerce.dto.CategoryResponseDTO;
import com.guilhermy.ecommerce.exception.ResourceNotFoundException;
import com.guilhermy.ecommerce.mapper.CategoryMapper;
import com.guilhermy.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    public CategoryResponseDTO create(CategoryRequestDTO requestDTO) {
        Category category = categoryMapper.toEntity(requestDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(savedCategory);
    }
    
    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return categoryMapper.toResponseDTO(category);
    }
    
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    public CategoryResponseDTO update(Long id, CategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        
        category.setName(requestDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(updatedCategory);
    }
    
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada");
        }
        categoryRepository.deleteById(id);
    }
} 