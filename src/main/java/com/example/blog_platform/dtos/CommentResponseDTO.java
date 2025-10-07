package com.example.blog_platform.dtos;

import com.example.blog_platform.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;

    private Long authorId;
    private String authorUsername;

    private int likeCount;
    public CommentResponseDTO(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.authorId = comment.getAuthor().getId();
        this.authorUsername = comment.getAuthor().getUserName();
        this.likeCount = comment.getLikes().size();
    }
}
