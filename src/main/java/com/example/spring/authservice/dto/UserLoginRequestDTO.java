package com.example.spring.authservice.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserLoginRequestDTO {
    private String userId;
    private String password;
}
