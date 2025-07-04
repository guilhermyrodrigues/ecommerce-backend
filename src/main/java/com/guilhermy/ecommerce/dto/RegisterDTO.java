package com.guilhermy.ecommerce.dto;

import com.guilhermy.ecommerce.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private Role role;

}