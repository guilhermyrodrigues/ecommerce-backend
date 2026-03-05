package com.guilhermy.ecommerce.controller;

import com.guilhermy.ecommerce.domain.User;
import com.guilhermy.ecommerce.enums.Role;
import com.guilhermy.ecommerce.repository.UserRepository;
import com.guilhermy.ecommerce.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void findAllShouldAllowAdmin() throws Exception {
        when(orderService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "customer@test.com", roles = "CUSTOMER")
    void findAllShouldForbidCustomer() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "customer@test.com", roles = "CUSTOMER")
    void findMyOrdersShouldAllowAuthenticatedCustomer() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("customer@test.com");
        user.setRole(Role.CUSTOMER);

        when(userRepository.findByEmail("customer@test.com")).thenReturn(Optional.of(user));
        when(orderService.findByUserId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/orders/my-orders"))
                .andExpect(status().isOk());
    }
}
