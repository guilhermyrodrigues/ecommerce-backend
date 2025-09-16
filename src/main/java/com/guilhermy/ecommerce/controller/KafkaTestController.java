package com.guilhermy.ecommerce.controller;

import com.guilhermy.ecommerce.dto.PagamentoAprovadoEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/kafka/test")
@RequiredArgsConstructor
@Slf4j
public class KafkaTestController {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @GetMapping("/health")
    public String health() {
        return "Kafka Test Controller está funcionando!";
    }
    
    @PostMapping("/pagamento-aprovado")
    public String testPagamentoAprovado(@RequestBody PagamentoAprovadoEventDTO event) {
        try {
            kafkaTemplate.send("pagamento.aprovado", event);
            log.info("Evento de teste enviado para pagamento.aprovado: {}", event.getOrderId());
            return "Evento enviado com sucesso!";
        } catch (Exception e) {
            log.error("Erro ao enviar evento de teste: {}", e.getMessage());
            return "Erro ao enviar evento: " + e.getMessage();
        }
    }
} 