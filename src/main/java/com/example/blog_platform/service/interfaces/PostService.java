package com.example.blog_platform.service.interfaces;

import com.example.blog_platform.entity.Post;

import java.util.List;

public interface PostService {
   public Post createPost(Long userId , Post post);
   public Post updatePost(Long userId, Long postId, Post updatedPost);
   public Post deletePost(Long userId, Long postId);
   public Post getPostById(Long postId);
   public List<Post>listPostsByUser(Long userId);
   public List<Post> searchPosts(String keyword);
}
