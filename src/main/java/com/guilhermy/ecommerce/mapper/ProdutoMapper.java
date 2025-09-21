package com.guilhermy.ecommerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.guilhermy.ecommerce.domain.Product;
import com.guilhermy.ecommerce.dto.ProductResponseDTO;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

	ProductResponseDTO toDto(Product entidade);
	
	List<ProductResponseDTO> toDtoList(List<Product> list);
}
