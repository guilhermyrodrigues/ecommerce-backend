package com.guilhermy.ecommerce.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{
	
	private static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";
	
	public ProdutoNaoEncontradoException() {
		super(PRODUTO_NAO_ENCONTRADO);
	}

}
