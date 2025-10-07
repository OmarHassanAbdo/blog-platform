package com.example.blog_platform.dtos;

import com.example.blog_platform.entity.Comment;
import com.example.blog_platform.entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String text;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long authorId;
    private String authorUsername;

    private int likeCount;
    private int commentCount;

    private List<CommentResponseDTO> comments;
    public PostResponseDTO(Post post){
        this.id = post.getId();
        this.text = post.getText();
        this.image = post.getImageUrl();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.authorId = post.getAuthor().getId();
        this.authorUsername = post.getAuthor().getUserName();
        this.likeCount = post.getLikes().size();
        this.commentCount = post.getComments().size();
        this.comments = post.getComments().stream()
                .map(
                        comment -> new CommentResponseDTO(comment)
                ).toList();;
    }
}
