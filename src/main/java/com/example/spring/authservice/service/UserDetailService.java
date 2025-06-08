package com.example.spring.authservice.service;

import com.example.spring.authservice.config.security.CustomUserDetails;
import com.example.spring.authservice.domain.User;
import com.example.spring.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUserId = userRepository.findByUserId(username);

        if (byUserId == null) {
            throw new UsernameNotFoundException(username + " not found.");
        }

        return CustomUserDetails.builder()
                .user(byUserId)
                .roles( List.of(String.valueOf(byUserId.getRole())) )
                .build();
    }
}
