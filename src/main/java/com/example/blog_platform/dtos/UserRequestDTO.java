package com.example.blog_platform.dtos;

import com.example.blog_platform.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String bio;
    private String profilePhoto;
    public static User toUser(UserRequestDTO userRequestDTO , Long userId){
        User user = new User();
        user.setId(userId);
        user.setUserName(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setBio(userRequestDTO.getBio());
        user.setProfilePhoto(userRequestDTO.getProfilePhoto());
        return user;
    }
}
