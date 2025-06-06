package com.example.spring.authservice.controller;

import com.example.spring.authservice.dto.UserJoinRequestDTO;
import com.example.spring.authservice.dto.UserJoinResponseDTO;
import com.example.spring.authservice.dto.UserLoginRequestDTO;
import com.example.spring.authservice.dto.UserLoginResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class UserApiController {

    @PostMapping("/join")
    public UserJoinResponseDTO join(@RequestBody UserJoinRequestDTO userJoinRequestDTO) {
        log.info("join: {}", userJoinRequestDTO);
        return UserJoinResponseDTO.builder()
                .isSuccess(true)
                .build();
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginRequestDTO requestDTO) {
        log.info("login: {}", requestDTO);
        return UserLoginResponseDTO.builder()
                .userName("test")
                .build();
    }
}
