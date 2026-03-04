package com.guilhermy.ecommerce.service;

import com.guilhermy.ecommerce.domain.Order;
import com.guilhermy.ecommerce.dto.PagamentoAprovadoEventDTO;
import com.guilhermy.ecommerce.enums.OrderStatus;
import com.guilhermy.ecommerce.exception.ResourceNotFoundException;
import com.guilhermy.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "pagamento.aprovado", groupId = "ecommerce-group")
    @Transactional
    public void handlePagamentoAprovado(PagamentoAprovadoEventDTO event) {
        log.info("Recebido evento de pagamento aprovado para o pedido: {}", event.getOrderId());

        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + event.getOrderId()));

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        log.info("Status do pedido {} atualizado para PAID", event.getOrderId());
    }
}
