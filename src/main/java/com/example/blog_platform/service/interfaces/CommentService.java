package com.example.blog_platform.service.interfaces;

import com.example.blog_platform.entity.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> addComment(Long userId, Long postId, Comment comment);
    public Comment updateComment(Long userId, Long commentId, Comment updatedComment);
    public Comment deleteComment(Long userId, Long commentId);
    public List<Comment>getCommentsForPost(Long postId);
    public Long countCommentsForPost(Long postId);

    public Comment getCommentById(Long commentId);
}
