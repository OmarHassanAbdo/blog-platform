package com.example.blog_platform.Controller;

import com.example.blog_platform.dtos.CommentRequestDTO;
import com.example.blog_platform.dtos.CommentResponseDTO;
import com.example.blog_platform.entity.Comment;
import com.example.blog_platform.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<List<CommentResponseDTO>>addCommentToPost(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId,
            @RequestBody CommentRequestDTO commentRequest
            ){
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        List<Comment>comments =commentService.addComment(userId , postId , comment);
        List<CommentResponseDTO>commentResponses= comments.stream().map(
                comment1 -> new CommentResponseDTO(comment)
        ).toList();
        return ResponseEntity.ok(commentResponses);
    }
    @PutMapping("/{commentId}/user/{userId}")
    public ResponseEntity<CommentResponseDTO>updateComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId,
            @RequestBody CommentRequestDTO updatedComment
    ){
        Comment comment = new Comment();
        comment.setText(updatedComment.getText());
        CommentResponseDTO commentResponse = new CommentResponseDTO(commentService.updateComment(userId , commentId , comment));
        return ResponseEntity.ok(commentResponse);
    }
    @DeleteMapping("/{commentId}/user/{userId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId
    ){
        commentService.deleteComment(userId , commentId);
        return ResponseEntity.ok("comment deleted successfully");
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDTO>>getCommentsForPost(
            @PathVariable("postId") Long postId
    ){
        List<Comment> comments = commentService.getCommentsForPost(postId);
        List<CommentResponseDTO>commentResponseDTOS = comments.stream()
                .map(
                        comment -> new CommentResponseDTO(comment)
                ).toList();
        return ResponseEntity.ok(commentResponseDTOS);
    }
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long>getNumberOfCommentsForPost(
            @PathVariable("postId") Long postId
    ){
        return ResponseEntity.ok(commentService.countCommentsForPost(postId));
    }
}
