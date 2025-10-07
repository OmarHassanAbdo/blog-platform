package com.example.blog_platform.service;

import com.example.blog_platform.entity.Comment;
import com.example.blog_platform.entity.Like;
import com.example.blog_platform.entity.Post;
import com.example.blog_platform.entity.User;
import com.example.blog_platform.repository.LikeRepository;
import com.example.blog_platform.service.interfaces.CommentService;
import com.example.blog_platform.service.interfaces.LikeService;
import com.example.blog_platform.service.interfaces.PostService;
import com.example.blog_platform.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeRepository likeRepository;

    @Override
    public Like likePost(Long userId, Long postId) {
        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new RuntimeException("User has already liked this post.");
        }

        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setCreatedAt(LocalDateTime.now());

        return likeRepository.save(like);
    }

    @Override
    public Like likeComment(Long userId, Long commentId) {
        if (likeRepository.existsByUserIdAndCommentId(userId, commentId)) {
            throw new RuntimeException("User has already liked this comment.");
        }

        User user = userService.getUserById(userId);
        Comment comment = commentService.getCommentById(commentId);

        Like like = new Like();
        like.setUser(user);
        like.setComment(comment);
        like.setCreatedAt(LocalDateTime.now());

       return likeRepository.save(like);
    }

    @Override
    public void unlikePost(Long userId, Long postId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new RuntimeException("Like not found."));
        likeRepository.delete(like);
    }

    @Override
    public void unlikeComment(Long userId, Long commentId) {
        Like like = likeRepository.findByUserIdAndCommentId(userId, commentId)
                .orElseThrow(() -> new RuntimeException("Like not found."));
        likeRepository.delete(like);
    }

    @Override
    public Long countLikesOnPost(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    @Override
    public Long countLikesOnComment(Long commentId) {
        return likeRepository.countByCommentId(commentId);
    }

    @Override
    public Boolean hasUserLikedPost(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    @Override
    public Boolean hasUserLikedComment(Long userId, Long commentId) {
        return likeRepository.existsByUserIdAndCommentId(userId, commentId);
    }
}
