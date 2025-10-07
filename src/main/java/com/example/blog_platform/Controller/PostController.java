package com.example.blog_platform.Controller;

import com.example.blog_platform.dtos.PostRequestDTO;
import com.example.blog_platform.dtos.PostResponseDTO;
import com.example.blog_platform.entity.Post;
import com.example.blog_platform.service.interfaces.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<PostResponseDTO> createPostForUser(
            @PathVariable Long userId,
            @Valid @RequestBody PostRequestDTO postRequest) {
        Post post = new Post();
        post.setImageUrl(postRequest.getImage());
        post.setText(postRequest.getText());
        Post createdPost = postService.createPost(userId, post);
        PostResponseDTO postResponseDTO = new PostResponseDTO(createdPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
    }

    @PutMapping("/{postId}/user/{userId}")
    public ResponseEntity<PostResponseDTO> updatePostForUser(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @Valid @RequestBody PostRequestDTO postRequest) {
        Post post = new Post();
        post.setText(postRequest.getText());
        post.setImageUrl(postRequest.getImage());
        Post updatedPost = postService.updatePost(userId, postId, post);
        PostResponseDTO postResponseDTO = new PostResponseDTO(updatedPost);
        return ResponseEntity.ok(postResponseDTO);
    }

    @DeleteMapping("/{postId}/user/{userId}")
    public ResponseEntity<Void> deletePostForUser(
            @PathVariable Long postId,
            @PathVariable Long userId) {

        postService.deletePost(userId, postId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        PostResponseDTO postResponseDTO = new PostResponseDTO(post);
        return ResponseEntity.ok(postResponseDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsForUser(@PathVariable Long userId) {
        List<PostResponseDTO>postsDto = postService.listPostsByUser(userId).stream()
                .map(
                        post -> new PostResponseDTO(post)
                ).toList();
        return ResponseEntity.ok(postsDto);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO>postResponseDTOS = postService.getAllPosts()
                .stream().map(
                        post -> new PostResponseDTO(post)
                ).toList();

        return ResponseEntity.ok(postResponseDTOS);
    }

    @GetMapping("/public")
    public ResponseEntity<List<PostResponseDTO>> getPublicPosts() {
        List<PostResponseDTO>postResponseDTOS = postService.getAllPosts()
                .stream().map(
                        post -> new PostResponseDTO(post)
                ).toList();

        return ResponseEntity.ok(postResponseDTOS);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDTO>> searchPosts(@RequestParam String keyword) {
        List<PostResponseDTO>postResponseDTOS = postService.searchPosts(keyword)
                .stream().map(
                        post -> new PostResponseDTO(post)
                ).toList();

        return ResponseEntity.ok(postResponseDTOS);
    }
}
