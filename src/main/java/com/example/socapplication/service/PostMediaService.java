package com.example.socapplication.service;

import com.example.socapplication.enums.postMedia.MediaType;
import com.example.socapplication.model.dto.postMediaDto.AddPostMedia;
import com.example.socapplication.model.dto.postMediaDto.ResponsePostMedia;
import com.example.socapplication.model.dto.postMediaDto.UpdatePostMedia;
import com.example.socapplication.model.entity.Post;
import com.example.socapplication.model.entity.PostMedia;
import com.example.socapplication.repository.PostMediaRepository;
import com.example.socapplication.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class PostMediaService {

    private final PostMediaRepository postMediaRepository;
    private final PostRepository postRepository;

    public PostMediaService(PostMediaRepository postMediaRepository, PostRepository postRepository) {
        this.postMediaRepository = postMediaRepository;
        this.postRepository = postRepository;
    }

    public List<ResponsePostMedia> findAll() {
        return postMediaRepository.findAll()
                .stream()
                .map(media -> new ResponsePostMedia(
                        media.getId(),
                        media.getPostId().getId(),
                        media.getMediaType().name(),
                        media.getUrl(),
                        media.getSortOrder()
                ))
                .toList();
    }

    public ResponsePostMedia findById(Long id) {
        return postMediaRepository.findById(id)
                .map(media -> new ResponsePostMedia(
                        media.getId(),
                        media.getPostId().getId(),
                        media.getMediaType().name(),
                        media.getUrl(),
                        media.getSortOrder()
                ))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<ResponsePostMedia> findByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return postMediaRepository.findByPostId_Id(postId)
                .stream()
                .map(media -> new ResponsePostMedia(
                        media.getId(),
                        media.getPostId().getId(),
                        media.getMediaType().name(),
                        media.getUrl(),
                        media.getSortOrder()
                ))
                .toList();
    }

    public void createPostMedia(AddPostMedia dto) {
        Post post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        PostMedia media = new PostMedia();
        media.setPostId(post);
        media.setMediaType(MediaType.valueOf(dto.mediaType().toUpperCase()));
        media.setUrl(dto.url());
        media.setSortOrder(dto.sortOrder());

        postMediaRepository.save(media);
    }

    public void updatePostMedia(Long id, UpdatePostMedia dto) {
        PostMedia media = postMediaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        media.setMediaType(MediaType.valueOf(dto.mediaType().toUpperCase()));
        media.setUrl(dto.url());
        media.setSortOrder(dto.sortOrder());

        postMediaRepository.save(media);
    }

    public void deletePostMedia(Long id) {
        if (!postMediaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        postMediaRepository.deleteById(id);
    }

}