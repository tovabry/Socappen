package com.example.socapplication.model.dto.postDto;

import java.time.OffsetDateTime;

public record ResponsePost(
        Long id,
        Long userId,
        String title,
        String content,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
