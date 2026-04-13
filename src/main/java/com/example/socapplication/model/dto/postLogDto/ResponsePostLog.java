package com.example.socapplication.model.dto.postLogDto;

import java.time.OffsetDateTime;

public record ResponsePostLog(
        Long id,
        Long appUserId,
        Long postId,
        String ipAddress,
        OffsetDateTime createdAt) {
}