package com.parcial2.models.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String email;
    private String password;
}
