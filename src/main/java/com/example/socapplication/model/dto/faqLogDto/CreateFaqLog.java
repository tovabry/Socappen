package com.example.socapplication.model.dto.faqLogDto;

import java.time.OffsetDateTime;

public record CreateFaqLog(
        Long appUserId,
        Long faqId,
        String ipAddress,
        OffsetDateTime createdAt
) {
}
