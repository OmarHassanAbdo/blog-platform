package com.example.blog_platform.dtos;

import lombok.Data;

@Data
public class LoginResponse {
    String token;
    String tokenType;
    long expiresAt;
}
