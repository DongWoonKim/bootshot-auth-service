package com.example.spring.authservice.controller;

import com.example.spring.authservice.dto.TokenRequestDTO;
import com.example.spring.authservice.dto.TokenResponseDTO;
import com.example.spring.authservice.dto.ValidTokenRequestDTO;
import com.example.spring.authservice.dto.ValidTokenResponseDTO;
import com.example.spring.authservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public TokenResponseDTO getToken(@RequestBody TokenRequestDTO tokenRequestDTO) {
        return tokenService.getToken(tokenRequestDTO.getRefreshToken());
    }

    @PostMapping("/token/validate")
    public ValidTokenResponseDTO validateToken(@RequestBody ValidTokenRequestDTO validTokenRequestDTO) {
        return tokenService.validToken(validTokenRequestDTO.getToken());
    }

}
