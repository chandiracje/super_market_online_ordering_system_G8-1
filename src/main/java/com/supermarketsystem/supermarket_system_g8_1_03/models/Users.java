package com.supermarketsystem.supermarket_system_g8_1_03.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(length = 255)
    private String defaultShippingAddress;

    @Column(nullable = false)
    private boolean isEmailVerified = false;
}