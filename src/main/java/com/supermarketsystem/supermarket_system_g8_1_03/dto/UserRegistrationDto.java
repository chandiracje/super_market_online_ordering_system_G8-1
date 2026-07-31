package com.supermarketsystem.supermarket_system_g8_1_03.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String defaultShippingAddress;
}