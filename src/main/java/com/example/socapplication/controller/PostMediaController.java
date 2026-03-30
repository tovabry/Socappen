package com.example.socapplication.controller;

import com.example.socapplication.model.dto.postMediaDto.AddPostMedia;
import com.example.socapplication.model.dto.postMediaDto.ResponsePostMedia;
import com.example.socapplication.model.dto.postMediaDto.UpdatePostMedia;
import com.example.socapplication.service.PostMediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostMediaController {

    private final PostMediaService postMediaService;

    public PostMediaController(PostMediaService postMediaService) {
        this.postMediaService = postMediaService;
    }

    @GetMapping("/media")
    public ResponseEntity<List<ResponsePostMedia>> findAll() {
        return ResponseEntity.ok(postMediaService.findAll());
    }

    @GetMapping("/{postId}/media")
    public ResponseEntity<List<ResponsePostMedia>> findByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(postMediaService.findByPostId(postId));
    }

    @GetMapping("/media/{id}")
    public ResponseEntity<ResponsePostMedia> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postMediaService.findById(id));
    }

    @PostMapping("/media")
    public ResponseEntity<Void> createPostMedia(@RequestBody AddPostMedia dto) {
        postMediaService.createPostMedia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/media/{id}")
    public ResponseEntity<Void> updatePostMedia(@PathVariable Long id, @RequestBody UpdatePostMedia dto) {
        postMediaService.updatePostMedia(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/media/{id}")
    public ResponseEntity<Void> deletePostMedia(@PathVariable Long id) {
        postMediaService.deletePostMedia(id);
        return ResponseEntity.noContent().build();
    }

}