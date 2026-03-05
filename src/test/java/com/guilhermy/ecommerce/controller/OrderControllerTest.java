package com.guilhermy.ecommerce.controller;

import com.guilhermy.ecommerce.domain.User;
import com.guilhermy.ecommerce.dto.OrderRequestDTO;
import com.guilhermy.ecommerce.dto.OrderResponseDTO;
import com.guilhermy.ecommerce.exception.ResourceNotFoundException;
import com.guilhermy.ecommerce.repository.UserRepository;
import com.guilhermy.ecommerce.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private Authentication authentication;

    @Test
    void createOrderShouldUseAuthenticatedUserId() {
        OrderRequestDTO request = new OrderRequestDTO(Collections.emptyList());
        OrderResponseDTO responseDTO = new OrderResponseDTO();

        User user = new User();
        user.setId(42L);
        user.setEmail("customer@test.com");

        when(authentication.getName()).thenReturn("customer@test.com");
        when(userRepository.findByEmail("customer@test.com")).thenReturn(Optional.of(user));
        when(orderService.createOrder(request, 42L)).thenReturn(responseDTO);

        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(request, authentication);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(responseDTO, response.getBody());
        verify(orderService).createOrder(request, 42L);
    }

    @Test
    void createOrderShouldThrowWhenAuthenticatedUserDoesNotExist() {
        OrderRequestDTO request = new OrderRequestDTO(Collections.emptyList());

        when(authentication.getName()).thenReturn("missing@test.com");
        when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderController.createOrder(request, authentication));

        verify(orderService, never()).createOrder(any(), anyLong());
    }
}
