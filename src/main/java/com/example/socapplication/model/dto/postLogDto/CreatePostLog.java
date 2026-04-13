package com.example.socapplication.model.dto.postLogDto;

import java.time.OffsetDateTime;

public record CreatePostLog(
        Long appUserId,
        Long postId,
        String ipAddress,
        OffsetDateTime createdAt
) {
}
