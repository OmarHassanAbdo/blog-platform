package com.example.blog_platform.security.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private String username;

    private String password;
}
