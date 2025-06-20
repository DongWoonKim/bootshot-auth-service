package com.example.spring.authservice.service;

import com.example.spring.authservice.domain.User;
import com.example.spring.authservice.dto.TokenResponseDTO;
import com.example.spring.authservice.dto.ValidTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProviderService tokenProviderService;

    public ValidTokenResponseDTO validToken(String token) {
        int result = tokenProviderService.validToken(token);
        return ValidTokenResponseDTO
                .builder()
                .statusNum(result)
                .build();
    }

    public TokenResponseDTO getToken(String refreshToken) {
        final int TOKEN_VALID = 1;

        int result = tokenProviderService.validToken(refreshToken);

        if (result != TOKEN_VALID) {
            return TokenResponseDTO.builder()
                    .status(result)
                    .accessToken(null)
                    .refreshToken(null)
                    .build();
        }

        User tokenDetails = tokenProviderService.getTokenDetails(refreshToken);

        String accessToken = tokenProviderService.generateToken(tokenDetails, Duration.ofHours(2));
        String newRefreshToken = tokenProviderService.generateToken(tokenDetails, Duration.ofDays(2));

        return TokenResponseDTO.builder()
                .status(result)
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
