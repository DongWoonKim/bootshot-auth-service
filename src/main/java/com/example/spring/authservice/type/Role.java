package com.example.spring.authservice.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER,
    ROLE_ADMIN;
}
