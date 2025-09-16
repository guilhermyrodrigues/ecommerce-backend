package com.guilhermy.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoAprovadoEventDTO {
    private Long orderId;
    private String paymentId;
    private String status;
    private Instant approvedAt;
    private Double amount;
} 