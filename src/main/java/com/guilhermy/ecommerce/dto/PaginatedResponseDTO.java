package com.guilhermy.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "DTO genérico para respostas paginadas")
public class PaginatedResponseDTO<T> {
    @Schema(description = "Conteúdo da página atual")
    private List<T> content;

    @Schema(description = "Número da página atual (base 0)", example = "0")
    private int page;

    @Schema(description = "Quantidade de elementos por página", example = "10")
    private int size;

    @Schema(description = "Total de elementos", example = "42")
    private long totalElements;

    @Schema(description = "Total de páginas", example = "5")
    private int totalPages;

    @Schema(description = "Indica se é a última página", example = "false")
    private boolean last;
}
