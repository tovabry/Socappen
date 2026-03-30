package com.example.socapplication.model.dto.postDto;

public record AddPost(
        Long userId,
        String title,
        String content
) {
}
