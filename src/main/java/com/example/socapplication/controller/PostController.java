package com.example.socapplication.controller;

import com.example.socapplication.model.dto.postDto.AddPost;
import com.example.socapplication.model.dto.postDto.ResponsePost;
import com.example.socapplication.model.dto.postDto.UpdatePost;
import com.example.socapplication.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<ResponsePost>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePost> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResponsePost>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody AddPost dto) {
        postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody UpdatePost dto) {
        postService.updatePost(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}