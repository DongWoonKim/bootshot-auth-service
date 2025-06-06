package com.example.spring.authservice.model;

import com.example.spring.authservice.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class User {
    private long id;
    private String userId;
    private String password;
    private String userName;
    private Role role;
}
