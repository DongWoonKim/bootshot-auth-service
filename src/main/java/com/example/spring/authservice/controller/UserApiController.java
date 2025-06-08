package com.example.spring.authservice.controller;

import com.example.spring.authservice.domain.User;
import com.example.spring.authservice.dto.UserJoinRequestDTO;
import com.example.spring.authservice.dto.UserJoinResponseDTO;
import com.example.spring.authservice.dto.UserLoginRequestDTO;
import com.example.spring.authservice.dto.UserLoginResponseDTO;
import com.example.spring.authservice.service.TokenProviderService;
import com.example.spring.authservice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auths")
public class UserApiController {

    private final UserService userService;
    private final TokenProviderService tokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public UserJoinResponseDTO join(@RequestBody UserJoinRequestDTO userJoinRequestDTO) {
        log.info("join: {}", userJoinRequestDTO);

        return userService.join(
                userJoinRequestDTO.toUser(bCryptPasswordEncoder)
        );
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login( @RequestBody UserLoginRequestDTO requestDTO ) {
        log.info("login: {}", requestDTO);

        User user = userService.login(requestDTO);

        // Access Token 생성 (짧은 유효기간)
        String accessToken = tokenProvider.generateToken(user, Duration.ofDays(1));

        // Refresh Token 생성 (긴 유효기간)
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(10));

        // Refresh Token을 HttpOnly 쿠키에 저장
        return UserLoginResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
