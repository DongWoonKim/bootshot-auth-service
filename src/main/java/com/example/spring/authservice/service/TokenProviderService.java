package com.example.spring.authservice.service;

import com.example.spring.authservice.config.jwt.JwtProperties;
import com.example.spring.authservice.domain.User;
import com.example.spring.authservice.dto.ClaimsResponseDTO;
import com.example.spring.authservice.type.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProviderService {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredAt.toMillis());
        return buildToken(expiry, user);
    }

    private String buildToken(Date expiry, User user) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .setSubject(user.getUserId())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .claim("userName", user.getUserName())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public int validToken(String token) {
        try {
            getClaims(token);
            return 1;
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            log.info("Token이 만료되었습니다.");
            return 2;
        } catch (Exception e) {
            // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            log.info("err : {}", e.getMessage());
            return -1;
        }
    }

    public ClaimsResponseDTO getAuthentication(String token) {
        Claims claims = getClaims(token);

        return ClaimsResponseDTO.builder()
                .userId(claims.getSubject())
                .roles(List.of(claims.get("role").toString()))
                .build();
    }

    // 토큰에서 Subject, id, role 값을 추출하는 메서드
    public User getTokenDetails(String token) {
        Claims claims = getClaims(token);

        return User.builder()
                .id(claims.get("id", Long.class))
                .userId(claims.getSubject())
                .userName(claims.get("userName", String.class))
                .role(Role.valueOf(claims.get("role", String.class)))
                .build();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
