package com.example.blog_platform.Controller;

import com.example.blog_platform.dtos.LikeResponseDTO;
import com.example.blog_platform.service.interfaces.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {
    @Autowired
    LikeService likeService;
    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<?>likePost(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") Long userId){
        LikeResponseDTO likeResponseDTO = new LikeResponseDTO(likeService.likePost(userId,postId));
        return ResponseEntity.ok(likeResponseDTO);
    }
    @DeleteMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<?>unlikePost(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") Long userId
    ){
        likeService.unlikePost(userId , postId);
        return ResponseEntity.ok("like deleted");
    }
    @PostMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<?>likeComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId){
        LikeResponseDTO likeResponseDTO = new LikeResponseDTO(likeService.likeComment(userId,commentId));
        return ResponseEntity.ok(likeResponseDTO);
    }
    @DeleteMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<?>unlikeComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId
    ){
        likeService.unlikePost(userId , commentId);
        return ResponseEntity.ok("like deleted");
    }
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<?>getNumberOfLikeToPost(
            @PathVariable("postId") Long postId
    ){
        return ResponseEntity.ok(likeService.countLikesOnPost(postId));
    }
    @GetMapping("/comment/{commentId}/count")
    public ResponseEntity<?>getNumberOfLikeToComment(
            @PathVariable("commentId") Long commentId
    ){
        return ResponseEntity.ok(likeService.countLikesOnComment(commentId));
    }
    @GetMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<?>DidUserLikedPost(
            @PathVariable("postId") Long postId,
            @PathVariable("userId") Long userId
    ){
        Boolean isLiked = likeService.hasUserLikedPost(userId , postId);
        return ResponseEntity.ok(isLiked);
    }
    @GetMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<?>DidUserLikedComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId
    ){
        Boolean isLiked = likeService.hasUserLikedComment(userId , commentId);
        return ResponseEntity.ok(isLiked);
    }

}
