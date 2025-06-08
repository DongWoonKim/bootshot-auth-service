package com.example.spring.authservice.service;

import com.example.spring.authservice.config.security.CustomUserDetails;
import com.example.spring.authservice.domain.User;
import com.example.spring.authservice.dto.UserJoinResponseDTO;
import com.example.spring.authservice.dto.UserLoginRequestDTO;
import com.example.spring.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    
    public User login(UserLoginRequestDTO userLoginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.getUserId(),
                        userLoginRequestDTO.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

    public UserJoinResponseDTO join(User user) {
        if (isDuplicateUserId(user.getUserId())) {
            return failureResponse();
        }

        userRepository.save(user);
        return successResponse();
    }

    private boolean isDuplicateUserId(String userId) {
        return userId != null && userRepository.existsByUserId(userId);
    }

    private UserJoinResponseDTO successResponse() {
        return UserJoinResponseDTO.builder()
                .isSuccess(true)
                .build();
    }

    private UserJoinResponseDTO failureResponse() {
        return UserJoinResponseDTO.builder()
                .isSuccess(false)
                .build();
    }

}
