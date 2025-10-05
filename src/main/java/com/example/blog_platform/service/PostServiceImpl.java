package com.example.blog_platform.service;

import com.example.blog_platform.entity.Post;
import com.example.blog_platform.entity.User;
import com.example.blog_platform.repository.PostRepository;
import com.example.blog_platform.service.interfaces.UserService;
import com.example.blog_platform.service.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    UserService userService;
    @Autowired
    PostRepository postRepository;

    @Override
    public Post createPost(Long userId, Post post) {
        User user = userService.getUserById(userId);
        post.setAuthor(user);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    @Override
    public Post updatePost(Long userId, Long postId, Post updatedPost) {
        Post existingPost = getPostById(postId);
        User user = userService.getUserById(userId);

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> r.getRoleName().name().equals("ROLE_ADMIN"));

        if (!existingPost.getAuthor().getId().equals(userId) && !isAdmin) {
            throw new RuntimeException("You are not allowed to edit this post.");
        }

        existingPost.setText(updatedPost.getText());
        existingPost.setImageUrl(updatedPost.getImageUrl());
        existingPost.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(existingPost);
    }


    @Override
    public Post deletePost(Long userId, Long postId) {
        Post post = getPostById(postId);
        User user = userService.getUserById(userId);
        boolean isAdmin = user.getRoles().stream()
                        .anyMatch( r ->
                                r.getRoleName().name().equals("ROLE_ADMIN"));
        if(!user.getId().equals(post.getAuthor().getId()) && !isAdmin){
            throw new RuntimeException("deleting action is not authorized");
        }
        postRepository.deleteById(postId);
        return post;
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("post isn't found")
        );
    }

    @Override
    public List<Post> listPostsByUser(Long userId) {
        return postRepository.findByAuthorId(userId);
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTextContainingIgnoreCase(keyword);
    }

}
