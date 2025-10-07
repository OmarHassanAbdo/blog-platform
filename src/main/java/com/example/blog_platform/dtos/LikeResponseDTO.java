package com.example.blog_platform.dtos;

import com.example.blog_platform.entity.Like;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LikeResponseDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long postId;
    private Long commentId;
    private LocalDateTime createdAt;
    public LikeResponseDTO(Like like){
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.username = like.getUser().getUserName();
        this.postId = like.getPost().getId();
        this.commentId = like.getComment().getId();
        this.createdAt = like.getCreatedAt();
    }
}
