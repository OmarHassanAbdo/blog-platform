package com.example.blog_platform.security.requests;

import com.example.blog_platform.entity.enums.AppRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Signup {
    @NotBlank
    @Size(max = 20)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


    @NotBlank
    @Size(max = 120)
    @Column(name = "password", nullable = false, length = 120)
    private String password;
}
