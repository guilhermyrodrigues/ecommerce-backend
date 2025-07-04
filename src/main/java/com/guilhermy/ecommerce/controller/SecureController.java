package com.guilhermy.ecommerce.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public String onlyForCustomers() {
        return "Acesso autorizado para CUSTOMER!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String onlyForAdmins() {
        return "Acesso autorizado para ADMIN!";
    }
}
