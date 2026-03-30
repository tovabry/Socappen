package com.example.socapplication.model.dto.postMediaDto;

public record AddPostMedia(
        Long postId,
        String mediaType,
        String url,
        Integer sortOrder
) {
}
