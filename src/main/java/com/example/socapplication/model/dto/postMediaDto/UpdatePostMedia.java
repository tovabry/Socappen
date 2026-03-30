package com.example.socapplication.model.dto.postMediaDto;

public record UpdatePostMedia(
        String mediaType,
        String url,
        Integer sortOrder
) {
}
