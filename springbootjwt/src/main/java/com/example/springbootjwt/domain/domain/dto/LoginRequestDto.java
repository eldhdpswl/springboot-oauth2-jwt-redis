package com.example.springbootjwt.domain.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
