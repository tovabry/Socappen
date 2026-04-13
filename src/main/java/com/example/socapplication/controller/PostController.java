package com.example.socapplication.controller;

import com.example.socapplication.model.dto.postDto.AddPost;
import com.example.socapplication.model.dto.postDto.ResponsePost;
import com.example.socapplication.model.dto.postDto.UpdatePost;
import com.example.socapplication.model.dto.postLogDto.CreatePostLog;
import com.example.socapplication.model.entity.Post;
import com.example.socapplication.service.PostLogService;
import com.example.socapplication.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostLogService postLogService;

    public PostController(PostService postService, PostLogService postLogService) {
        this.postService = postService;
        this.postLogService = postLogService;
    }

    @GetMapping
    public ResponseEntity<List<ResponsePost>> findAll(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.findAll(page, size));
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
    public ResponseEntity<Void> createPost(@RequestBody AddPost dto, HttpServletRequest request) {
        Post post = postService.createPost(dto);

        String ip = getClientIp(request);

        postLogService.log(new CreatePostLog(
                dto.userId(),
                post.getId(),
                ip,
                OffsetDateTime.now()
        ));

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

    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}