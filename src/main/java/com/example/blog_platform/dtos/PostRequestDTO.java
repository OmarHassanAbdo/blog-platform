package com.example.blog_platform.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequestDTO {
    @NotBlank
    private String text;
    private String image;
}
