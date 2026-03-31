package com.example.socapplication.model.dto.messageLogDto;

import java.time.OffsetDateTime;

public record ResponseMessageLog(
        Long id,
        Long appUserId,
        Long conversationId,
        String ipAddress,
        OffsetDateTime createdAt
) {}
