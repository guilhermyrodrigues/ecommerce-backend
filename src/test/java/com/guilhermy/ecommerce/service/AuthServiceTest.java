package com.guilhermy.ecommerce.service;

import com.guilhermy.ecommerce.domain.User;
import com.guilhermy.ecommerce.dto.RegisterDTO;
import com.guilhermy.ecommerce.enums.Role;
import com.guilhermy.ecommerce.exception.ConflictException;
import com.guilhermy.ecommerce.jwt.JwtUtil;
import com.guilhermy.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerShouldForceCustomerRole() {
        RegisterDTO dto = new RegisterDTO();
        dto.setName("User Test");
        dto.setEmail("user@test.com");
        dto.setPassword("123456");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encoded-password");

        authService.register(dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("User Test", savedUser.getName());
        assertEquals("user@test.com", savedUser.getEmail());
        assertEquals("encoded-password", savedUser.getPassword());
        assertEquals(Role.CUSTOMER, savedUser.getRole());
    }

    @Test
    void registerShouldThrowConflictWhenEmailAlreadyExists() {
        RegisterDTO dto = new RegisterDTO();
        dto.setName("User Test");
        dto.setEmail("user@test.com");
        dto.setPassword("123456");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(ConflictException.class, () -> authService.register(dto));
        verify(userRepository, never()).save(any());
    }
}
