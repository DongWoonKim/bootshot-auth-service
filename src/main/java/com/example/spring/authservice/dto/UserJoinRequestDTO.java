package com.example.spring.authservice.dto;

import com.example.spring.authservice.type.Role;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserJoinRequestDTO {
    private String userId;
    private String password;
    private String userName;
    private Role role;


}
