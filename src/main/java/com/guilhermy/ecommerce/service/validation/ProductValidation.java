package com.guilhermy.ecommerce.service.validation;

import org.springframework.stereotype.Component;

import com.guilhermy.ecommerce.domain.Product;
import com.guilhermy.ecommerce.exception.ProdutoNaoEncontradoException;
import com.guilhermy.ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ProductValidation {

	private final ProductRepository productRepository;
	
	public Product validaIdEBuscaProduto(Long id) {
		return productRepository.findById(id).orElseThrow(ProdutoNaoEncontradoException::new);
	}
}
