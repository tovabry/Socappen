package com.example.socapplication.model.dto.postMediaDto;

public record ResponsePostMedia(
        Long postId,
        String mediaType,
        String url,
        Integer sortOrder
) {
}
