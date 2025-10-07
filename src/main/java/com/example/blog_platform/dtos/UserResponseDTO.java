package com.example.blog_platform.dtos;

import com.example.blog_platform.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private String profilePhoto;

    private List<String> roles;
    private List<Long> friendIds;
    public UserResponseDTO(User user){
        this.id = user.getId();
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.bio = user.getBio();
        this.profilePhoto = user.getProfilePhoto();
        this.roles = user.getRoles().stream().map(
                role -> role.getRoleName().name()
        ).toList();
        this.friendIds = user.getFriends().stream()
                .map(
                        f -> f.getId()
                ).toList();
    }
}
