package com.example.blog_platform.service.interfaces;

import com.example.blog_platform.entity.Like;

public interface LikeService {
   public Like likePost(Long userId, Long postId);
   public Like likeComment(Long userId, Long commentId);
   public void unlikePost(Long userId, Long postId);
   public void unlikeComment(Long userId, Long commentId);
   public Long countLikesOnPost(Long postId);
   public Long countLikesOnComment(Long commentId);
   public Boolean hasUserLikedPost(Long userId, Long postId);
   public Boolean hasUserLikedComment(Long userId, Long commentId);
}
