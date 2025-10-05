package com.example.blog_platform.service;

import com.example.blog_platform.entity.Comment;
import com.example.blog_platform.entity.Post;
import com.example.blog_platform.entity.User;
import com.example.blog_platform.repository.CommentRepository;
import com.example.blog_platform.service.interfaces.CommentService;
import com.example.blog_platform.service.interfaces.PostService;
import com.example.blog_platform.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> addComment(Long userId, Long postId, Comment comment) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);

        comment.setAuthor(user);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        List<Comment>comments = post.getComments();
        comments.add(comment);
        return comments;
    }

    @Override
    public Comment updateComment(Long userId, Long commentId, Comment updatedComment) {
        User user = userService.getUserById(userId);
        Comment oldComment = getCommentById(commentId);

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> r.getRoleName().name().equals("ROLE_ADMIN"));
        boolean isOwner = oldComment.getAuthor().getId().equals(userId);
        boolean isPostOwner = oldComment.getPost().getAuthor().getId().equals(userId);

        if (!isAdmin && !isOwner && !isPostOwner) {
            throw new RuntimeException("You are not authorized to update this comment.");
        }

        oldComment.setText(updatedComment.getText());
        oldComment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(oldComment);
    }

    @Override
    public Comment deleteComment(Long userId, Long commentId) {
        User user = userService.getUserById(userId);
        Comment comment = getCommentById(commentId);

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> r.getRoleName().name().equals("ROLE_ADMIN"));
        boolean isOwner = comment.getAuthor().getId().equals(userId);
        boolean isPostOwner = comment.getPost().getAuthor().getId().equals(userId);

        if (!isAdmin && !isOwner && !isPostOwner) {
            throw new RuntimeException("Deleting comment is not an authorized action.");
        }

        commentRepository.delete(comment);
        return comment;
    }

    @Override
    public List<Comment> getCommentsForPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Long countCommentsForPost(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment id not found."));
    }
}
