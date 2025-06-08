package com.example.spring.authservice.domain;

import com.example.spring.authservice.type.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA가 요구하는 기본 생성자는 열어주되, 외부에서는 직접 사용하지 못하게 막기 위해서
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String role;


    @Builder
    public User(Long id, String userId, String password, String userName, Role role) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.role = role.name();
    }
}
