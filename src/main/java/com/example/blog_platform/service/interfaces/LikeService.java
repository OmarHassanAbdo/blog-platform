package com.example.blog_platform.service.interfaces;

public interface LikeService {
   public void likePost(Long userId, Long postId);
   public void likeComment(Long userId, Long commentId);
   public void unlikePost(Long userId, Long postId);
   public void unlikeComment(Long userId, Long commentId);
   public Long countLikesOnPost(Long postId);
   public Long countLikesOnComment(Long commentId);
   public Boolean hasUserLikedPost(Long userId, Long postId);
   public Boolean hasUserLikedComment(Long userId, Long commentId);
}
