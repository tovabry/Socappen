package com.example.socapplication.service;

import com.example.socapplication.model.dto.postDto.AddPost;
import com.example.socapplication.model.dto.postDto.ResponsePost;
import com.example.socapplication.model.dto.postDto.UpdatePost;
import com.example.socapplication.model.entity.AppUser;
import com.example.socapplication.model.entity.Post;
import com.example.socapplication.repository.AppUserRepository;
import com.example.socapplication.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AppUserRepository appUserRepository;

    public PostService(PostRepository postRepository, AppUserRepository appUserRepository) {
        this.postRepository = postRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<ResponsePost> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable)
                .stream()
                .map(post -> new ResponsePost(
                        post.getId(),
                        post.getAppUserId().getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ))
                .toList();
    }

    public ResponsePost findById(Long id) {
        return postRepository.findById(id)
                .map(post -> new ResponsePost(
                        post.getId(),
                        post.getAppUserId().getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<ResponsePost> findByUserId(Long userId) {
        if (!appUserRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return postRepository.findByAppUserId_Id(userId)
                .stream()
                .map(post -> new ResponsePost(
                        post.getId(),
                        post.getAppUserId().getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ))
                .toList();
    }

    public Post createPost(AddPost dto) {
        AppUser user = appUserRepository.findById(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Post post = new Post();
        post.setAppUserId(user);
        post.setTitle(dto.title());
        post.setContent(dto.content());
        post.setCreatedAt(OffsetDateTime.now());
        post.setUpdatedAt(OffsetDateTime.now());

        return postRepository.save(post);
    }

    public void updatePost(Long id, UpdatePost dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        post.setContent(dto.content());
        post.setUpdatedAt(OffsetDateTime.now());

        postRepository.save(post);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        postRepository.deleteById(id);
    }

}